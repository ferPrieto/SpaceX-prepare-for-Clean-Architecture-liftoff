# Domain Layer Refactoring Analysis

## Current Structure

```
domain/
├── model/
│   ├── CompanyInfoDomainModel.kt  → Used by feature-dashboard
│   └── LaunchDomainModel.kt        → Used by feature-launches
├── usecase/
│   ├── GetCompanyInfo.kt           → Used by feature-dashboard
│   └── GetLaunches.kt              → Used by feature-launches  
├── mapper/
│   └── LaunchesDomainFilter.kt     → Used by feature-launches
└── SpaceXRepository.kt              → Shared interface

data/
├── model/ (Repository models)
├── mapper/ (Domain mappers)
├── repository/
│   └── SpaceXRepositoryImpl.kt     → Implements SpaceXRepository
└── SpaceXRemoteSource.kt

data-api/
├── model/ (API response models)
├── mapper/ (Repository mappers)
├── data/
│   └── SpaceXRemoteSourceImpl.kt
└── ApiService.kt
```

## Recommended Refactoring Strategy

### Option A: Feature-First Architecture (Recommended)

**Pros:**
- True modular architecture
- Each feature is self-contained
- Features can be developed/tested independently
- Follows Clean Architecture strictly

**Structure:**
```
feature-dashboard/
├── domain/
│   ├── model/
│   │   └── CompanyInfoDomainModel.kt
│   ├── usecase/
│   │   └── GetCompanyInfo.kt
│   └── repository/
│       └── CompanyInfoRepository.kt (interface)
└── presentation/
    └── ... (existing)

feature-launches/
├── domain/
│   ├── model/
│   │   └── LaunchDomainModel.kt
│   ├── usecase/
│   │   ├── GetLaunches.kt
│   │   └── LaunchesDomainFilter.kt
│   └── repository/
│       └── LaunchesRepository.kt (interface)
└── presentation/
    └── ... (existing)

core-data/  (renamed from data)
├── model/ (Repository models - internal)
├── mapper/ (Domain mappers)
└── repository/
    ├── CompanyInfoRepositoryImpl.kt → implements feature-dashboard's interface
    └── LaunchesRepositoryImpl.kt    → implements feature-launches's interface

core-network/ (renamed from data-api)
├── model/ (API responses)
├── mapper/
└── data/
    └── SpaceXRemoteSourceImpl.kt
```

### Option B: Hybrid Approach (Pragmatic)

**Pros:**
- Less refactoring needed
- Shared domain models reduce duplication
- Simpler dependency graph

**Structure:**
```
core-domain/  (renamed from domain)
├── model/
│   ├── CompanyInfoDomainModel.kt  (shared)
│   └── LaunchDomainModel.kt       (shared)
└── repository/
    └── SpaceXRepository.kt         (shared interface)

feature-dashboard/
├── domain/
│   └── usecase/
│       └── GetCompanyInfo.kt
└── presentation/

feature-launches/
├── domain/
│   ├── usecase/
│   │   └── GetLaunches.kt
│   └── mapper/
│       └── LaunchesDomainFilter.kt
└── presentation/

core-data/
└── repository/
    └── SpaceXRepositoryImpl.kt

core-network/
└── ... (API layer)
```

## Implementation Steps

### For Option A (Recommended):

1. **Create feature-dashboard repository interface:**
   ```kotlin
   // feature-dashboard/domain/repository/CompanyInfoRepository.kt
   interface CompanyInfoRepository {
       suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel>
   }
   ```

2. **Move domain models to features:**
   - `CompanyInfoDomainModel` → `feature-dashboard/domain/model/`
   - `LaunchDomainModel` → `feature-launches/domain/model/`

3. **Move use cases to features:**
   - `GetCompanyInfo` → `feature-dashboard/domain/usecase/`
   - `GetLaunches` → `feature-launches/domain/usecase/`
   - `LaunchesDomainFilter` → `feature-launches/domain/mapper/`

4. **Rename core modules:**
   - `domain` → DELETE (or keep as shared contracts if needed)
   - `data` → `core-data`
   - `data-api` → `core-network`

5. **Update implementations in core-data:**
   - Split `SpaceXRepositoryImpl` into feature-specific implementations
   - Implement feature-specific repository interfaces

### For Option B (Pragmatic):

1. **Rename modules:**
   - `domain` → `core-domain`
   - `data` → `core-data`
   - `data-api` → `core-network`

2. **Move use cases to features:**
   - `GetCompanyInfo` → `feature-dashboard/domain/usecase/`
   - `GetLaunches` → `feature-launches/domain/usecase/`
   - `LaunchesDomainFilter` → `feature-launches/domain/mapper/`

3. **Keep models in core-domain** (shared)

4. **Update dependencies:**
   - Features depend on `core-domain` for models and repository interface
   - Features depend on `core-data` (provided at runtime)

## Recommendation

I recommend **Option A** for the following reasons:

1. **True Modularity**: Each feature is completely independent
2. **Scalability**: Easy to add new features without touching core
3. **Testing**: Features can be tested in isolation
4. **Team Collaboration**: Different teams can own different features
5. **Follows Best Practices**: Aligns with the Clean Architecture example you referenced

However, **Option B** is acceptable if:
- You want faster implementation
- The app won't grow significantly
- Models are truly shared across features

## Next Steps

Which option would you prefer? I can implement either approach.

