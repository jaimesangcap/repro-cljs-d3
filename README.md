# repro-cljs-d3

`$ npm i`

`$ shadow-cljs watch app`

go to `http://localhost:9000`


```
app.js:2226 failed to load shadow.module.app.append.js TypeError: curve is not a function
    at SVGPathElement.line (d3-shape.js:349)
    at SVGPathElement.eval (d3-selection.js:399)
    at Selection.each (d3-selection.js:366)
    at Selection.attr (d3-selection.js:423)
    at Object.repro_cljs_d3$core$init [as init] (core.cljs:77)
    at eval (shadow.module.app.append.js:4)
    at eval (<anonymous>)
    at Object.goog.globalEval (app.js:836)
    at Object.env.evalLoad (app.js:2224)
    at app.js:2404
```
