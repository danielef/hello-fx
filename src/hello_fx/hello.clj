(ns hello-fx.hello
  (:gen-class :extends
              javafx.application.Application))

(defn button [text]
  (doto (javafx.scene.control.Button.)
    (.setText (str "Say " text))
    (.setOnAction (proxy [javafx.event.EventHandler] []
                    (handle [event]
                      (println text))))))

(defn hide [stage]
  (javafx.application.Platform/runLater 
   (fn []
     (try 
       (println (pr-str {:system-tray-support (java.awt.SystemTray/isSupported)}))
       (if (java.awt.SystemTray/isSupported)
         (.hide stage))
       (catch Throwable e
         (.printStackTrace e))))))

(defn create-tray-icon [stage]
  (if (java.awt.SystemTray/isSupported)
    (let [tray (java.awt.SystemTray/getSystemTray)
          tray-icon-size (-> tray (.getTrayIconSize))
          _ (println (pr-str {:width (.-width tray-icon-size) :height (.-height tray-icon-size) :type :gif}))
          icon (try 
                 (-> (ClassLoader/getSystemResource "foo.gif")
                     (javax.imageio.ImageIO/read)
                     (.getScaledInstance (.-width tray-icon-size)
                                         (.-height tray-icon-size)
                                         java.awt.Image/SCALE_SMOOTH))
                 (catch Exception e
                   (.printStackTrace e)))
          popup (java.awt.PopupMenu.)
          on-show-l  (reify java.awt.event.ActionListener
                       (actionPerformed [_ action-event]
                         (javafx.application.Platform/runLater
                          #(.show stage))))
          
          tray-icon (doto (java.awt.TrayIcon. icon "Foo!" popup)
                      (.addActionListener on-show-l))
          
          on-close-r (reify javafx.event.EventHandler
                       (handle [_ window-event]
                         (hide stage)))

          on-close-l (reify java.awt.event.ActionListener
                       (actionPerformed [_ action-event]
                         (System/exit 0)))
          on-show (doto (java.awt.MenuItem. "show")
                    (.addActionListener on-show-l))
          on-close (doto (java.awt.MenuItem. "close")
                     (.addActionListener on-close-l))
          ]
      (.setOnCloseRequest stage on-close-r)
      (.add popup (java.awt.MenuItem. "Foo"))
      (.addSeparator popup)
      (.add popup on-show)
      (.add popup on-close)
      (-> tray
          (.add tray-icon)))))

(defn -start [this primary-stage]
  (let [root (doto (javafx.scene.layout.StackPane.)
               (-> (.getChildren)
                   (.add (button "Hello World"))))]
    (println (pr-str {:primary-stage primary-stage}))

    (create-tray-icon primary-stage)
    (javafx.application.Platform/setImplicitExit false)
    (.setScene primary-stage (javafx.scene.Scene. root 300 300))
    #_(.initStyle primary-stage javafx.stage.StageStyle/TRANSPARENT)
    (.setAlwaysOnTop primary-stage true)
    (.show primary-stage)
    (hide primary-stage)))

(defn -main [& args]
  (javafx.application.Application/launch hello_fx.hello 
                                         (into-array String args)))
