# MSR17 Paper: Code Quality on GitHub
In this repo, we provide information on how to replicate our MSR 2017 study which examined the code quality of open-source reposiotires on GitHub by using static code analysis.

### Static Analysis Tool
For our static analyses, we use [ConQAT](https://www.cqse.eu/en/products/conqat/overview/), a free and open-source analysis tool developed by the folks at [CQSE GmbH](https://www.cqse.eu/en/).

### Analysis settings

We analyzed repositories in C, C++, C#, Java, and JavaScript and configured ConQAT to calculate the following metrics:

| Metric            | Setting                                                 |
| ----------------- | ------------------------------------------------------- |
| Clone Coverage    | `min. clone length`: 10, `max-instances`: 50            |
| Nesting Depth     | Nesting <= 5: 'green', else 'red'                       |
| Method Length     | method <= 75 SLOC: 'green', else 'red'                  |
| File Size         | file <= 750 SLOC: 'green', else 'red'                   |
| Comment Incompl.  | #commented interfaces / #expected interface comments[1] |

[1] To retrieve the _expected interface comments_, we used the following ConQAT comment selectors:
- For C and C++ : `public & (type | method | property | attribute ) & !(simpleGetter | simpleSetter | annotated(Override) | override)`
- For Java and C# : `type|(public&(attribute|(method&declaration)))` 

### Excluding irrelevant code
This repo contains resources for excluding test, generated and third-party code during static code analyses.
