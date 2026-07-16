# Backend Contract

The JSON shapes the renderer expects from the backend.

For how this data is consumed, see [renderer-flow.md](./renderer-flow.md).

---

## Endpoints

| Method | Path | Response |
|--------|------|----------|
| `GET` | `/ui-definitions` | `UiDefinitionsDto` |
| `GET` | `/feed/{screenId}` | `FeedDto` |

**Base URL (Android emulator):** `http://10.0.2.2:3000/mock/dynui`

Full paths:

- `GET http://10.0.2.2:3000/mock/dynui/ui-definitions`
- `GET http://10.0.2.2:3000/mock/dynui/feed/{screenId}`

---

## Serialization Rules

The client deserializes JSON with these settings:

| Setting | Value | Effect |
|---------|-------|--------|
| `classDiscriminator` | `"type"` | Polymorphic types identified by a `"type"` field |
| `ignoreUnknownKeys` | `true` | Extra JSON fields are ignored |
| `explicitNulls` | `false` | Missing nullable fields default to `null` |

Polymorphic types (components and actions) **must** include `"type"` in the JSON object.

---

## Definitions

**`GET /ui-definitions`**

Returns reusable layout templates and styles.

### Root shape

```json
{
  "layouts": [ /* LayoutDefinition[] */ ],
  "styles": [ /* StyleDefinition[] */ ]
}
```

### Layout

```json
{
  "id": "pokemon_card_layout",
  "root": { /* Component — see below */ }
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | `string` | yes | Unique layout identifier, referenced by feed items |
| `root` | `Component` | yes | Root component of the layout tree |

### Style

```json
{
  "id": "card_title",
  "width": "wrap",
  "height": "wrap",
  "padding": "8,8,8,8",
  "margin": "0,0,0,0",
  "spacing": 8,
  "backgroundColor": "#FFFFFF",
  "textColor": "#212121",
  "fontSize": 16,
  "fontWeight": "bold",
  "cornerRadius": "8,8,8,8",
  "alignment": "start"
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | `string` | yes | Unique style identifier |
| `width` | `string` | no | `"fill"`, `"wrap"`, or a fixed dp integer as string (e.g. `"120"`) |
| `height` | `string` | no | Same as `width` |
| `padding` | `string` | no | Four comma-separated ints: `top,right,bottom,left` |
| `margin` | `string` | no | Four comma-separated ints: `top,right,bottom,left` |
| `spacing` | `int` | no | Gap between children in dp (for stacks/lists) |
| `backgroundColor` | `string` | no | CSS-style color string |
| `textColor` | `string` | no | CSS-style color string |
| `fontSize` | `int` | no | Font size in sp |
| `fontWeight` | `string` | no | `"normal"`, `"medium"`, `"semibold"`, or `"bold"` |
| `cornerRadius` | `string` | no | Four comma-separated ints: `topStart,topEnd,bottomEnd,bottomStart` |
| `alignment` | `string` | no | `"start"`, `"center"`, or `"end"` |

Components reference styles via `"styleId": "card_title"`.

**Parsing notes:**

- Invalid `width` / `height` / `fontWeight` / `alignment` values throw at map time
- `padding`, `margin`, and `cornerRadius` require exactly four comma-separated integers

---

## Components

Every component is a JSON object with a **`"type"`** field. All components share these optional base fields:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `string` | Component identifier |
| `styleId` | `string` | Reference to a style `id` |
| `action` | `Action` | Tap/action handler (see Actions) |

### `text`

```json
{
  "type": "text",
  "id": "pokemon_name",
  "styleId": "card_title",
  "text": "Charizard",
  "binding": null,
  "action": null
}
```

| Field | Type | Description |
|-------|------|-------------|
| `text` | `string` | Static text. Used when `binding` is not set |
| `binding` | `string` | Key into feed item `data`. Used when `text` is not set |

Provide **either** `text` or `binding`, not both. If both are missing, rendered text is empty.

### `image`

```json
{
  "type": "image",
  "id": "pokemon_image",
  "url": "https://example.com/sprite.png",
  "binding": null
}
```

| Field | Type | Description |
|-------|------|-------------|
| `url` | `string` | Static image URL. Used when `binding` is not set |
| `binding` | `string` | Key into feed item `data`. Used when `url` is not set |

Provide **either** `url` or `binding`. If both are missing, rendered URL is empty.

### `stack`

```json
{
  "type": "stack",
  "id": "header_stack",
  "orientation": "vertical",
  "children": [ /* Component[] */ ]
}
```

| Field | Type | Description |
|-------|------|-------------|
| `orientation` | `string` | `"vertical"` or `"horizontal"` |
| `children` | `Component[]` | Nested components |

Any value other than `"horizontal"` (case-insensitive) is treated as **vertical**.

### `card`

```json
{
  "type": "card",
  "id": "pokemon_card",
  "children": [ /* Component[] */ ]
}
```

| Field | Type | Description |
|-------|------|-------------|
| `children` | `Component[]` | Nested components |

### `list`

```json
{
  "type": "list",
  "id": "home_feed",
  "orientation": "vertical",
  "children": [ /* Component[] */ ]
}
```

| Field | Type | Description |
|-------|------|-------------|
| `orientation` | `string` | `"vertical"` or `"horizontal"` |
| `children` | `Component[]` | Nested components |

### Supported component types

| `type` value | Purpose |
|--------------|---------|
| `text` | Text label |
| `image` | Image from URL |
| `stack` | Linear vertical/horizontal container |
| `card` | Grouped container |
| `list` | List container |

---

## Full Definitions Example

```json
{
  "layouts": [
    {
      "id": "pokemon_card_layout",
      "root": {
        "type": "card",
        "id": "pokemon_card",
        "styleId": "card_container",
        "children": [
          {
            "type": "text",
            "id": "pokemon_name",
            "styleId": "card_title",
            "binding": "name"
          },
          {
            "type": "image",
            "id": "pokemon_image",
            "binding": "imageUrl"
          }
        ]
      }
    }
  ],
  "styles": [
    {
      "id": "card_container",
      "width": "fill",
      "backgroundColor": "#FFFFFF",
      "padding": "16,16,16,16",
      "cornerRadius": "12,12,12,12"
    },
    {
      "id": "card_title",
      "textColor": "#212121",
      "fontSize": 18,
      "fontWeight": "bold",
      "padding": "8,8,8,8",
      "alignment": "start"
    }
  ]
}
```

---

## Feed

**`GET /feed/{screenId}`**

Returns screen-specific content. Each item references a layout by ID and supplies binding data.

### Root shape

```json
{
  "items": [ /* FeedItem[] */ ]
}
```

### Feed item

```json
{
  "id": "item_1",
  "layoutId": "pokemon_card_layout",
  "data": {
    "name": "Charizard",
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"
  },
  "action": {
    "type": "navigate",
    "destination": "pokemon_detail",
    "params": { "id": "6" }
  }
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | `string` | yes | Unique item identifier |
| `layoutId` | `string` | yes | Must match a layout `id` from definitions |
| `data` | `object` | yes | Binding key → value map (see Bindings) |
| `action` | `Action` | no | Optional action for the whole item |

If `layoutId` does not match any cached layout, the item is **skipped**.

### Full feed example

```json
{
  "items": [
    {
      "id": "charizard",
      "layoutId": "pokemon_card_layout",
      "data": {
        "name": "Charizard",
        "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"
      },
      "action": {
        "type": "navigate",
        "destination": "pokemon_detail",
        "params": { "pokemonId": "6" }
      }
    },
    {
      "id": "bulbasaur",
      "layoutId": "pokemon_card_layout",
      "data": {
        "name": "Bulbasaur",
        "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
      }
    }
  ]
}
```

---

## Bindings

Bindings connect **layout templates** to **feed data**.

### In definitions

A component uses a binding key instead of a static value:

```json
{
  "type": "text",
  "id": "pokemon_name",
  "binding": "name"
}
```

The key `"name"` must exist in the feed item's `data` object.

### In feed data

Values in `data` are arbitrary JSON. The client maps them as follows:

| JSON type | Mapped to |
|-----------|-----------|
| `string` | String value |
| `number` | Number value |
| `boolean` | Boolean value |
| `null` | Null |
| `object` | Nested object (keys become binding keys) |
| `array` | List of values |

### Examples

```json
{
  "data": {
    "name": "Charizard",
    "level": 36,
    "isLegendary": false,
    "stats": {
      "hp": 78,
      "attack": 84
    },
    "types": ["fire", "flying"]
  }
}
```

Only **string** bindings are resolved for `text` and `image` components today (via `.asString()`). Other value types in `data` are stored but not used unless a component reads them.

### Resolution priority

For `text` and `image` components:

1. If `text` / `url` is set → use static value
2. Else if `binding` is set → look up key in `feedItem.data`
3. Else → empty string

---

## Actions

Actions use the **`"type"`** discriminator, same as components.

### `navigate`

```json
{
  "type": "navigate",
  "destination": "pokemon_detail",
  "params": {
    "pokemonId": "6"
  }
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `type` | `"navigate"` | yes | Discriminator |
| `destination` | `string` | yes | Target screen ID |
| `params` | `object` | no | String key-value pairs passed to the destination |

### `toast`

```json
{
  "type": "toast",
  "message": "Added to favorites!"
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `type` | `"toast"` | yes | Discriminator |
| `message` | `string` | yes | Message to display |

### Where actions appear

Actions can be attached to:

- Any **component** in a layout definition (`action` on the component)
- A **feed item** (`action` on the item)

Actions are passed through to runtime nodes. Execution (navigation, showing a toast) is handled by the Android app, not the shared module.

### Supported action types

| `type` value | Purpose |
|--------------|---------|
| `navigate` | Navigate to another screen |
| `toast` | Show a toast message |

---

## Quick Reference

### Definitions response

```text
{
  layouts: [{ id, root: Component }],
  styles:  [{
    id,
    width?, height?,              // "fill" | "wrap" | "<dp>"
    padding?, margin?,            // "t,r,b,l"
    spacing?,
    backgroundColor?, textColor?,
    fontSize?, fontWeight?,       // normal|medium|semibold|bold
    cornerRadius?,                // "ts,te,be,bs"
    alignment?                    // start|center|end
  }]
}
```

### Feed response

```text
{
  items: [{ id, layoutId, data: { key: JsonValue }, action? }]
}
```

### Component discriminator values

`text` · `image` · `stack` · `card` · `list`

### Action discriminator values

`navigate` · `toast`

---

## Related Docs

| Question | Document |
|----------|----------|
| How is data consumed? | [renderer-flow.md](./renderer-flow.md) |
| How do I add a component type? | [adding-a-component.md](./adding-a-component.md) |
| Project structure | [architecture.md](./architecture.md) |
| Full file listing | [module-structure.md](./module-structure.md) |
