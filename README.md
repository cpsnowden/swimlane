# Swim Lane

Swim Lane is a side-project aimed at showing lap swimming lane availability across
different UK swimming pools (operated by different vendors), preventing the need
to jump across different booking application to see availability and plan your
week.

Deployed UI - https://cpsnowden.github.io/swimlane-ui/

## Design

### Lane Availability Data Sources

The project is predicated on the availability of lane availability data from the
different vendors. Hence I will initially focus on determining feasible data sourcing for 3 example vendors which operate pools in Central London.

1. Everyone Active (https://www.everyoneactive.com)
   - Queen Mother Leisure Center (Victoria)
   - The Castle Center (Elephant and Castle)
2. Better (http://better.org.uk)
   - Vauxhall Leisure Center
   - Chelsea Sports Center
   - London Aquatics Center (Stratford)
3. Active Lambeth (http://active.lambeth.gov.uk)
   - Clapham Leisure Centre (formerly operated by Better)

The discovered apis can be found [here](./docs/Data%20Sources.md)

### Feature Triage

H (High) / M (Medium) / L (Low) - Priority

- H - To _see_ lane availability for a pool
- H - To _see_ lane availability for multiple a pools
- M - To save a set of favorite pools
- L - To _see_ pools near your location (and their availability)
