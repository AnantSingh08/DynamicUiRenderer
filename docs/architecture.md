# Dynamic UI Renderer

**Dynamic UI Renderer** is a Kotlin Multiplatform project that renders UI from backend JSON instead of hard-coded screens.

A server sends layout templates and screen content. The **shared** module fetches that data, resolves bindings, and produces a **`UiNode` tree** — a platform-agnostic description of what to display. The **androidApp** module maps those nodes to Jetpack Compose and handles actions (navigate, toast).

This approach lets teams update layouts and content without shipping a new app version for every UI change.

---

## Project Goals

- **Backend-controlled UI** — layouts and content driven by remote JSON
- **Shared renderer logic** — fetch, map, resolve, and cache in the KMP `shared` module
- **Native Android UI** — Jetpack Compose in `androidApp` for platform rendering
- **Kotlin Multiplatform business logic** — rendering pipeline lives in `commonMain`
- **Clean Architecture** — strict layers with clear dependency direction

---

## High Level Architecture

```mermaid
graph TB
    Backend["Backend APIs"]
    Shared["shared module\n(fetch · map · resolve)"]
    Android["androidApp\n(Jetpack Compose)"]

    Backend --> Shared
    Shared --> Android
```

The backend owns UI structure and data. Shared turns that into `UiNode` trees. Android draws them and executes actions.

---

## Project Structure

```text
DynamicUiRenderer/
├── shared/              # KMP rendering engine (commonMain)
│   ├── bootstrap/       # DynamicUi + DynamicUiRenderer (public API)
│   ├── data/            # Network, DTOs, mappers, repository implementations
│   ├── domain/          # Models, repository interfaces, use cases, value objects
│   ├── definition/      # Component templates (text, image, stack, card, list)
│   ├── runtime/         # Registries, binding resolver, runtime resolver
│   └── model/           # Resolved output types
│       ├── node/        # UiNode tree (Text, Image, Stack, Card, List)
│       ├── style/       # Style, Dimension, EdgeInsets, etc.
│       ├── action/      # NavigateAction, ToastAction
│       └── common/      # Orientation
│
└── androidApp/          # Android app — Compose UI, Hilt, UiRenderer
```

| Folder | What it does |
|--------|--------------|
| `bootstrap/` | Public entry points — `DynamicUi.createRenderer()` → `DynamicUiRenderer` |
| `data/` | Talks to the backend, deserializes JSON, maps DTOs to domain types |
| `domain/` | Business rules — use cases, domain models, repository contracts |
| `definition/` | Reusable layout templates fetched from the server |
| `runtime/` | Caches definitions, resolves bindings and styles into nodes |
| `model/node/` | Output types — the resolved `UiNode` tree ready for display |
| `model/style/` | Visual properties (colors, padding, dimensions, alignment, etc.) |
| `model/action/` | Actions attached to nodes (`Navigate`, `Toast`) |
| `androidApp/` | Compose screens, ViewModels, Hilt, and `UiRenderer` |

For a full file listing, see [module-structure.md](./module-structure.md).

---

## Data Flow

When you call `renderer.resolveScreen("home")`, this is what happens:

```mermaid
graph TD
    A["Definitions API"] --> B["Definitions Repository"]
    B --> C["Layout & Style Registries"]
    D["Feed API"] --> E["Feed Repository"]
    E --> F["Runtime Resolver"]
    C --> F
    F --> G["UiNode Tree"]
    G --> H["UiRenderer (Compose)"]
```

**Initialization (first call only)**

1. Fetch UI definitions from `/ui-definitions`
2. Map JSON → domain models
3. Store layouts and styles in in-memory registries

**Screen resolution (every call)**

1. Fetch feed data from `/feed/{screenId}`
2. For each feed item, look up its layout in the registry
3. Resolve bindings (dynamic text/URLs) and styles
4. Return a `List<UiNode>` — one tree root per feed item

**Android presentation**

1. ViewModels call `DynamicUiRenderer.resolveScreen(screenId)`
2. `UiRenderer` maps each `UiNode` to a Compose widget
3. Tap handlers execute `NavigateAction` / `ToastAction`

---

## Core Concepts

### Definitions

Reusable layout templates from the backend. A definition describes **structure** — which components exist, how they nest, and which styles or bindings they use. Definitions are fetched once and cached in registries.

### Feed

Screen-specific **content** from the backend. Each feed item references a layout by ID and provides binding data (dynamic values like names, URLs, or prices). Feed data is fetched per screen.

### Bindings

A link between a component and a value in feed data. For example, a `TextDefinition` with `binding: "pokemon_name"` reads that key from the feed item's data map and displays the resolved string.

### Runtime Nodes

The **output** of rendering. After bindings and styles are resolved, the engine produces a `UiNode` tree (`TextNode`, `ImageNode`, `StackNode`, `CardNode`, `ListNode`). These are fully resolved — no raw IDs or unresolved bindings remain.

### Registries

In-memory caches for definitions.

| Registry | Stores |
|----------|--------|
| `LayoutRegistry` | `Map<LayoutId, LayoutDefinition>` |
| `StyleRegistry` | `Map<StyleId, Style>` |

Populated during initialization, read during screen resolution.

### DynamicUiRenderer

The single entry point for the shared module. Call `resolveScreen(screenId)` and get back `List<UiNode>`. Definition loading happens automatically on the first call — callers never manage initialization themselves.

---

## Public API

Android (or any consumer) should only interact with the renderer through:

```kotlin
val renderer = DynamicUi.createRenderer()
val nodes: List<UiNode> = renderer.resolveScreen("home")
```

- **`DynamicUi`** — public factory; calls internal `RendererFactory`
- **`DynamicUiRenderer`** — hides initialization and use cases behind `resolveScreen`
- **`screenId`** — a plain `String` (e.g. `"home"`), because screen IDs are backend-owned

In `androidApp`, Hilt provides `DynamicUiRenderer` via `DynamicUiModule`. Everything else — APIs, mappers, registries, resolvers — is internal to `shared`.

---

## Why This Architecture?

| Decision | Reason |
|----------|--------|
| **DTO separation** | JSON types stay in `data/`. Domain and runtime never see raw DTOs. |
| **Runtime models (`UiNode`)** | Definitions hold unresolved IDs; nodes hold resolved values ready for UI. |
| **Registries** | Definitions change rarely — fetch once, cache in memory, reuse across screens. |
| **Value objects** | `ComponentId`, `LayoutId`, `StyleId`, `BindingKey` prevent mixing up identifiers. |
| **`String` for screen IDs** | Screens are backend-driven; no client recompile when new screens are added. |
| **`DynamicUiRenderer` as public API** | One method, one responsibility — consumers don't touch use cases or networking. |
| **Two use cases** | `InitializeDefinitionsUseCase` (load templates) and `ResolveScreenUseCase` (render a screen). |
| **Shared has no Compose** | Rendering logic is platform-agnostic; only `androidApp` knows about Compose. |
| **Hilt in Android only** | DI is a platform concern; the shared module stays framework-free. |

---

## Design Principles

- **Single Responsibility** — each package does one job (fetch, map, resolve, or display).
- **Separation of Concerns** — structure (definitions) is separate from content (feed) and output (nodes).
- **Backend Driven UI** — the server controls what the user sees without an app release.
- **Clean Architecture** — dependencies point inward; domain never depends on data or runtime details.
- **Dependency Inversion** — use cases depend on repository interfaces, not HTTP implementations.

---

## Current Scope

### Supported component types

| Type | Description |
|------|-------------|
| **Text** | Static text or binding-backed dynamic text |
| **Image** | Static URL or binding-backed dynamic URL |
| **Stack** | Vertical or horizontal container |
| **Card** | Grouped container with children |
| **List** | Scrollable list container |

### Supported actions

Actions are attached to nodes during resolution. The shared module carries them; `androidApp` executes them via `UiEvent` (navigate / toast).

| Action | Payload |
|--------|---------|
| **Navigate** | `destination: String`, optional `params` |
| **Toast** | `message: String` |

### Supported binding value types

`String`, `Number`, `Boolean`, nested `Object`, `List`, and `Null` — mapped to the `UiValue` hierarchy.

### API endpoints

| Endpoint | Purpose |
|----------|---------|
| `GET /ui-definitions` | Layout templates and styles |
| `GET /feed/{screenId}` | Screen content |

Base URL (emulator): `http://10.0.2.2:3000/mock/dynui`

---

## Related Documentation

| Document | Contents |
|----------|----------|
| [module-structure.md](./module-structure.md) | Full package and file listing |
| [renderer-flow.md](./renderer-flow.md) | Detailed step-by-step rendering pipeline |
| [backend-contract.md](./backend-contract.md) | JSON schemas and API contract |
| [adding-a-component.md](./adding-a-component.md) | How to add a new component type |
| [roadmap.md](./roadmap.md) | Future improvements |

---

*This document reflects the current codebase. For a deeper dive into any layer, see the related docs above.*
