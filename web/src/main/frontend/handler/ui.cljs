(ns frontend.handler.ui
  (:require [dommy.core :as dom]
            [frontend.state :as state]
            [frontend.db :as db]
            [rum.core :as rum]
            [goog.dom :as gdom]
            [frontend.util :as util :refer-macros [profile]]))

;; sidebars
(defn hide-left-sidebar
  []
  (dom/add-class! (dom/by-id "menu")
                  "md:block")
  (dom/remove-class! (dom/by-id "left-sidebar")
                     "enter")
  (dom/remove-class! (dom/by-id "search")
                     "sidebar-open")
  (dom/remove-class! (dom/by-id "main")
                     "sidebar-open"))

(defn show-left-sidebar
  []
  (dom/remove-class! (dom/by-id "menu")
                     "md:block")
  (dom/add-class! (dom/by-id "left-sidebar")
                  "enter")
  (dom/add-class! (dom/by-id "search")
                  "sidebar-open")
  (dom/add-class! (dom/by-id "main")
                  "sidebar-open"))

(defn hide-right-sidebar
  []
  (state/hide-right-sidebar!))

(defn show-right-sidebar
  []
  (state/open-right-sidebar!))

(defn toggle-right-sidebar!
  []
  (state/toggle-sidebar-open?!))

(defn re-render-root!
  []
  (when-let [component (state/get-root-component)]
    (db/clear-query-state!)
    (rum/request-render component)
    (doseq [component (state/get-custom-query-components)]
      (rum/request-render component))))


(defn highlight-element!
  [fragment]
  (when-let [element (gdom/getElement fragment)]
    (dom/add-class! element "block-highlight")
    (js/setTimeout #(dom/remove-class! element "block-highlight")
                   4000)))

(defn scroll-and-highlight!
  [state]
  (when-let [fragment (util/get-fragment)]
    (util/scroll-to-element fragment)
    (highlight-element! fragment))
  state)
