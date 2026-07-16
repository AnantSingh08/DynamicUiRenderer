# Roadmap

What's shipped today and what's planned next.

---

## Current

### Components

| Component | Status |
|-----------|--------|
| Text | ✅ Supported |
| Image | ✅ Supported |
| Stack | ✅ Supported |
| Card | ✅ Supported |
| List | ✅ Supported |

### Actions

| Action | Status |
|--------|--------|
| Navigate | ✅ Attached to nodes + handled in `androidApp` |
| Toast | ✅ Attached to nodes + handled in `androidApp` |

### Shared module

| Feature | Status |
|---------|--------|
| Definitions API + caching | ✅ |
| Feed API + binding resolution | ✅ |
| Style resolution (dimensions, insets, font, alignment) | ✅ |
| Runtime resolver (`UiNode` output) | ✅ |
| `DynamicUiRenderer.resolveScreen(screenId)` | ✅ |

### Android app

| Feature | Status |
|---------|--------|
| Jetpack Compose shell | ✅ |
| Dagger Hilt setup | ✅ |
| `UiNode` → Compose (`UiRenderer`) | ✅ |
| Action handling (navigate, toast via `UiEvent`) | ✅ |
| Home + Details screens wired to renderer | ✅ |

---

## Next

### Components

- **Button** — tappable CTA with label + action
- **Carousel** — horizontal paging content
- **Grid** — multi-column layout

### Platform

- **Animations** — enter/exit transitions on nodes
- **iOS** — SwiftUI renderer consuming `shared`
- **Web** — Compose Multiplatform or web renderer

### Infrastructure

- **Room cache** — offline definitions and feed storage
- **Definition invalidation** — refresh cached layouts when backend version changes
- **expect/actual HttpClient** — proper KMP network engine abstraction

---

## Related Docs

| Document | Contents |
|----------|----------|
| [architecture.md](./architecture.md) | Project structure |
| [module-structure.md](./module-structure.md) | Full package and file listing |
| [renderer-flow.md](./renderer-flow.md) | How rendering works |
| [adding-a-component.md](./adding-a-component.md) | How to add a new component |
| [backend-contract.md](./backend-contract.md) | JSON contract |
