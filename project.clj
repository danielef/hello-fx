(defproject hello-fx "0.1.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [fn-fx/fn-fx-javafx "0.5.0-SNAPSHOT"]]
  :repl-options {:init-ns hello-fx.core}
  :main hello-fx.core
  :aot [hello-fx.hello
        hello-fx.core])
