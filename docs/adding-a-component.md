# Adding a Component

How to add a new UI component to the renderer — using **Button** as an example.

Every component follows the same path through the codebase:

```text
Backend JSON
    ↓
DTO                    (data/dto)
    ↓
Definition             (definition/)
    ↓
Mapper                 (data/mapper)
    ↓
Runtime Node           (model/node/)
    ↓
Resolver               (runtime/resolver)
    ↓
Compose Renderer       (androidApp/renderer)
```

Touch **one layer at a time**. Never skip a step.

---

## Checklist

When adding a component (e.g. `button`), update these files:

| # | Layer | File | Action |
|---|-------|------|--------|
| 1 | JSON contract | [backend-contract.md](./backend-contract.md) | Document the new `type` |
| 2 | DTO | `data/dto/definitions/ButtonDefinitionDto.kt` | Create |
| 3 | Serialization | `data/network/SerializationModule.kt` | Register subclass |
| 4 | Definition | `definition/ButtonDefinition.kt` | Create |
| 5 | Mapper | `data/mapper/UiDefinitionsMapperImpl.kt` | Add `when` branch |
| 6 | Runtime node | `model/node/ButtonNode.kt` | Create |
| 7 | Resolver | `runtime/resolver/UiRuntimeResolverImpl.kt` | Add resolve logic |
| 8 | Compose | `androidApp/…/renderer/components/ButtonRenderer.kt` + `UiRenderer.kt` | Add Composable mapping |
| 9 | Tests | `shared/src/…Test/…` | Add mapper + resolver tests |

---

## Step 1 — Backend JSON

Define what the backend sends. Use `"type"` as the discriminator.

```json
{
  "type": "button",
  "id": "submit_btn",
  "styleId": "primary_button",
  "label": "Add to favorites",
  "binding": null,
  "action": {
    "type": "toast",
    "message": "Added!"
  }
}
```

Or with a dynamic label from feed data:

```json
{
  "type": "button",
  "id": "cta_button",
  "binding": "buttonLabel",
  "action": {
    "type": "navigate",
    "destination": "detail",
    "params": { "id": "6" }
  }
}
```

**Rules for JSON fields:**

- Use `string`, `int`, `boolean` — never value objects in JSON
- Use `"type": "button"` to identify the component
- `id`, `styleId`, `binding` are plain strings
- Follow the same optional fields as other components: `id`, `styleId`, `action`

Update [backend-contract.md](./backend-contract.md) with the new type before implementing.

---

## Step 2 — DTO

**File:** `shared/src/commonMain/kotlin/com/dynamicui/shared/data/dto/definitions/ButtonDefinitionDto.kt`

DTOs mirror JSON exactly. Primitives only.

```kotlin
@Serializable
@SerialName("button")
data class ButtonDefinitionDto(
    override val id: String,
    override val styleId: String? = null,
    override val action: UiActionDto? = null,
    val label: String? = null,
    val binding: String? = null,
) : ComponentDefinitionDto
```

**Pattern to copy:** `TextDefinitionDto.kt`

| Rule | Why |
|------|-----|
| `@SerialName("button")` must match JSON `"type"` | Polymorphic deserialization |
| Implement `ComponentDefinitionDto` | Shared base fields |
| `String?` for ids and bindings | DTOs never use value objects |
| No business logic | DTOs are data containers only |

---

## Step 3 — Serialization

**File:** `shared/src/commonMain/kotlin/com/dynamicui/shared/data/network/SerializationModule.kt`

Register the new DTO so kotlinx.serialization can deserialize it:

```kotlin
polymorphic(ComponentDefinitionDto::class) {
    subclass(TextDefinitionDto::class)
    subclass(ImageDefinitionDto::class)
    subclass(StackDefinitionDto::class)
    subclass(CardDefinitionDto::class)
    subclass(ListDefinitionDto::class)
    subclass(ButtonDefinitionDto::class)  // ← add this
}
```

If you skip this step, the API response will fail to deserialize.

---

## Step 4 — Definition

**File:** `shared/src/commonMain/kotlin/com/dynamicui/shared/definition/ButtonDefinition.kt`

Definitions use **value objects** for identifiers:

```kotlin
data class ButtonDefinition(
    override val id: ComponentId,
    override val styleId: StyleId? = null,
    override val action: UiAction? = null,
    val label: String? = null,
    val binding: BindingKey? = null,
) : ComponentDefinition
```

**Pattern to copy:** `TextDefinition.kt`

| Field | Type | Notes |
|-------|------|-------|
| `id` | `ComponentId` | Not `String` |
| `styleId` | `StyleId?` | Unresolved — looked up at runtime |
| `action` | `UiAction?` | Domain action, not `UiActionDto` |
| `label` | `String?` | Static text (actual content, not an identifier) |
| `binding` | `BindingKey?` | Dynamic label from feed data |

---

## Step 5 — Mapper

**File:** `shared/src/commonMain/kotlin/com/dynamicui/shared/data/mapper/UiDefinitionsMapperImpl.kt`

Add a branch in `mapComponent()`:

```kotlin
is ButtonDefinitionDto -> ButtonDefinition(
    id = ComponentId(dto.id),
    styleId = dto.styleId?.let(::StyleId),
    action = ActionMapper.map(dto.action),
    label = dto.label,
    binding = dto.binding?.let(::BindingKey),
)
```

**Pattern to copy:** the `TextDefinitionDto` branch in the same file.

| Rule | Why |
|------|-----|
| Wrap ids in value objects here | Single conversion point from JSON |
| Use `ActionMapper.map()` for actions | DTO → domain action conversion |
| No registry or network access | Mappers only convert DTO → domain |

---

## Step 6 — Runtime Node

**File:** `shared/src/commonMain/kotlin/com/dynamicui/shared/model/node/ButtonNode.kt`

Runtime nodes hold **resolved** values — no `StyleId`, no `BindingKey`:

```kotlin
data class ButtonNode(
    override val id: ComponentId,
    override val style: Style?,
    override val action: UiAction?,
    val label: String,
) : UiNode
```

**Pattern to copy:** `model/node/TextNode.kt`

Every `UiNode` must have:

```kotlin
val id: ComponentId
val style: Style?      // resolved, not StyleId
val action: UiAction?
```

Plus type-specific resolved fields (`label` for Button, `text` for Text, etc.).

---

## Step 7 — Resolver

**File:** `shared/src/commonMain/kotlin/com/dynamicui/shared/runtime/resolver/UiRuntimeResolverImpl.kt`

Two changes required.

### 7a — Add to `resolveComponent()` when block

```kotlin
is ButtonDefinition ->
    resolveButton(component, feedItem)
```

### 7b — Add resolve function

```kotlin
private fun resolveButton(
    definition: ButtonDefinition,
    feedItem: FeedItem
): ButtonNode {

    val resolvedLabel =
        definition.label
            ?: resolveBinding(definition.binding, feedItem)?.asString()
            ?: ""

    return ButtonNode(
        id = definition.id,
        style = resolveStyle(definition.styleId),
        action = definition.action,
        label = resolvedLabel,
    )
}
```

**Pattern to copy:** `resolveText()` in the same file.

| Concern | Handled by |
|---------|------------|
| Binding lookup | `resolveBinding()` → `BindingResolverImpl` |
| Style lookup | `resolveStyle()` → `StyleRegistry` |
| Action | Passed through unchanged |
| Children | Not applicable for Button (leaf node) |

Container components (stack, card, list) also call `resolveChildren()` — Button does not.

---

## Step 8 — Compose Renderer

**Location:** `androidApp/src/main/kotlin/com/dynamicui/renderer/`

### 8a — Add a component renderer

**File:** `…/renderer/components/ButtonRenderer.kt`

```kotlin
@Composable
fun ButtonRenderer(
    node: ButtonNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit = {},
) {
    Button(
        onClick = { node.action?.let(onAction) },
        modifier = modifier
            .then(node.style.toModifier())
            .clickableAction(node.action, onAction),
    ) {
        Text(node.label)
    }
}
```

### 8b — Register in `UiRenderer`

**File:** `…/renderer/UiRenderer.kt`

```kotlin
is ButtonNode ->
    ButtonRenderer(
        node = node,
        modifier = modifier,
        onAction = onAction,
    )
```

**Rules:**

- `shared` never imports Compose
- Action execution (navigate, toast) stays in ViewModels / screens via `UiEvent`
- Style → Modifier mapping uses existing mappers under `renderer/mappers`

**Pattern to copy:** `TextRenderer.kt` and the `TextNode` branch in `UiRenderer.kt`.

---

## End-to-End Example

Given this layout definition in `/ui-definitions`:

```json
{
  "id": "action_card",
  "root": {
    "type": "card",
    "id": "card",
    "children": [
      {
        "type": "text",
        "id": "title",
        "binding": "title"
      },
      {
        "type": "button",
        "id": "cta",
        "styleId": "primary_button",
        "binding": "buttonLabel",
        "action": { "type": "toast", "message": "Clicked!" }
      }
    ]
  }
}
```

And this feed item in `/feed/home`:

```json
{
  "id": "item_1",
  "layoutId": "action_card",
  "data": {
    "title": "Charizard",
    "buttonLabel": "View details"
  }
}
```

The resolver produces:

```text
CardNode
├── TextNode  (text = "Charizard")
└── ButtonNode (label = "View details", action = ToastAction("Clicked!"))
```

---

## Rules (Don't Break These)

| Rule | Details |
|------|---------|
| **DTOs use primitives** | `String`, `Int` — never `ComponentId` or `BindingKey` in DTOs |
| **Definitions use value objects** | `ComponentId`, `StyleId`, `BindingKey` for identifiers |
| **Runtime nodes are resolved** | `Style?` not `StyleId`, `label: String` not `binding: BindingKey` |
| **Mappers only convert** | No network, no registries, no runtime |
| **Runtime never talks to network** | Resolver reads registries and feed item data only |
| **Don't leak DTOs** | DTO imports stay inside `data/` |
| **Register serialization** | Every new DTO needs a `subclass()` in `SerializationModule` |
| **Exhaustive when blocks** | Add branches in `mapComponent()`, `resolveComponent()`, and `UiRenderer` |

---

## Adding Tests

Add tests when you add a component. Minimum coverage:

| Test | What to verify |
|------|----------------|
| **Mapper test** | `ButtonDefinitionDto` JSON → `ButtonDefinition` with correct value objects |
| **Resolver test** | `ButtonDefinition` + `FeedItem` → `ButtonNode` with resolved label and style |
| **Binding test** | Static `label` vs `binding` fallback to `""` |

Place tests in the shared module test source set.

---

## Quick Reference — Existing Components

Use these as copy-paste starting points:

| Component | DTO | Definition | Node | Compose | Has children? |
|-----------|-----|------------|------|---------|---------------|
| Text | `TextDefinitionDto` | `TextDefinition` | `TextNode` | `TextRenderer` | No |
| Image | `ImageDefinitionDto` | `ImageDefinition` | `ImageNode` | `ImageRenderer` | No |
| Stack | `StackDefinitionDto` | `StackDefinition` | `StackNode` | `StackRenderer` | Yes |
| Card | `CardDefinitionDto` | `CardDefinition` | `CardNode` | `CardRenderer` | Yes |
| List | `ListDefinitionDto` | `ListDefinition` | `ListNode` | `ListRenderer` | Yes |

Leaf components (text, image, button) resolve their own content.
Container components (stack, card, list) call `resolveChildren()` recursively.

---

## Related Docs

| Question | Document |
|----------|----------|
| JSON field reference | [backend-contract.md](./backend-contract.md) |
| How rendering works | [renderer-flow.md](./renderer-flow.md) |
| Project structure | [architecture.md](./architecture.md) |
| Full file listing | [module-structure.md](./module-structure.md) |
| What's planned next? | [roadmap.md](./roadmap.md) |
