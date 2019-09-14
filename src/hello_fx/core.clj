(ns hello-fx.core
  (:gen-class)
  (:require [hello-fx.hello :as hello]))

(defn -main [& args]
  (hello/-main args))
