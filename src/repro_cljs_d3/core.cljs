(ns repro-cljs-d3.core
  (:require ["d3" :as d3]))

(defn ^:export init []
  (let [margin {:top 20 :right 30 :bottom 30 :left 40}
        data (clj->js (map (fn [i] {"value" (+ 2 (rand-int 25))
                                    "date" (js/Date. (+ (.now js/Date)
                                                       (* i 86400000)))})
                        (range 1 30)))
        _ (js/console.log "data " data)
        svgHeight 300
        svgWidth 400
        height (- svgHeight (:top margin) (:bottom margin))
        width (- svgWidth (:left margin) (:right margin))
        svg (-> d3
              (.select "#chart")
              (.append "svg")
              (.attr "width" svgWidth)
              (.attr "height" svgHeight)
              (.append "g")
              (.attr "transform" (str "translate(" (:left margin) "," (:top margin) ")")))

        x (-> d3
            (.scaleUtc)
            (.domain (.extent d3 data #(.-date %)))
            (.range #js [(:left margin) (- width (:right margin))]))

        y (-> d3
            (.scaleLinear)
            (.domain #js [0, 20])
            (.nice)
            (.range #js [(- height (:bottom margin)) (:top margin)]))

        xAxis (fn [g]
                (-> g
                  (.attr "transform" (str "translate(0, " (- height (:bottom margin)) ")"))
                  (.call (-> (.axisBottom d3 x)
                           (.ticks (/ width 80))
                           (.tickSizeOuter 0)))))

        yAxis (fn [g]
                (-> g
                  (.attr "transform" (str "translate(" (:left margin) ", 0)"))
                  (.call (.axisLeft d3 y))
                  #_(.call #(-> %
                              (.select ".domain")
                              (.remove)))
                  (.call #(-> %
                            (.select ".tick:last-of-type text")
                            (.clone)
                            (.attr "x" 3)
                            (.attr "text-anchor" "start")
                            (.attr "font-weight" "bold")
                            (.text "$")))))

        _ (-> svg
            (.append "g")
            (xAxis))

        _ (-> svg
            (.append "g")
            (yAxis))

        line (-> d3
               (.line)
               (.curve (.curveNatural d3))
               (.defined #(not (js/isNaN (.-value %))))
               (.x #(x (.-date %)))
               (.y #(y (.-value %))))

        _ (-> svg
            (.append "path")
            (.datum data)
            (.attr "fill" "none")
            (.attr "stroke" "green")
            (.attr "stroke-width" 1.5)
            (.attr "d" line))]))