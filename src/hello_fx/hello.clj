(ns hello-fx.hello
  (:gen-class :extends
              javafx.application.Application))

(defn -start [this primary-stage]
  (.show primary-stage))

(defn -main [& args]
  (javafx.application.Application/launch hello_fx.hello 
                                         (into-array String args)))
