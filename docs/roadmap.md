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
| Navigate | ✅ Attached to nodes |
| Toast | ✅ Attached to nodes |

### Shared module

| Feature | Status |
|---------|--------|
| Definitions API + caching | ✅ |
| Feed API + binding resolution | ✅ |
| Style resolution | ✅ |
| Runtime resolver (`UiNode` output) | ✅ |
| `Renderer.resolveScreen(screenId)` | ✅ |

### Android app

| Feature | Status |
|---------|--------|
| Jetpack Compose shell | ✅ |
| Dagger Hilt setup | ✅ |
| `UiNode` → Compose renderer | ❌ Not yet wired |
| Action handling (navigate, toast) | ❌ Not yet wired |

---

## Next

### Components

- **Button** — tappable CTA with label + action
- **Carousel** — horizontal paging content
- **Grid** — multi-column layout

### Platform

- **Animations** — enter/exit transitions on nodes
- **Compose renderer** — map `UiNode` trees to Composables in `androidApp`
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
| [renderer-flow.md](./renderer-flow.md) | How rendering works |
| [adding-a-component.md](./adding-a-component.md) | How to add a new component |
| [backend-contract.md](./backend-contract.md) | JSON contract |
