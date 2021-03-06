(ns leiningen.new.lein-webapp-template
  (:refer-clojure :exclude [read])
  (:import [java.io ByteArrayOutputStream])
  (:use [clojure.java.io :as io]
        [leiningen.new.templates :only [renderer sanitize year ->files]]))

(def ^{:private true :const true} template-name "lein-webapp-template")

(def render-text (renderer template-name))

(defn resource-input
  "Get resource input stream. Usefull for binary resources like images."
  [resource-path]
  (-> (str "leiningen/new/" (sanitize template-name) "/" resource-path)
      io/resource
      io/input-stream))

(defn render 
  "Render the content of a resource"
  ([resource-path]
     (resource-input resource-path))
  ([resource-path data]
     (render-text resource-path data)))

(defn lein-webapp-template
  "Create a new webapp project based on Compojure, Stencil, Bootstrap and jQuery"
  [name]
  (let [data {:name name
              :sanitized (sanitize name)
              :year (year)}]
    (println "Generating the webapp project" (str name "..."))
    (->files data
             [".gitignore" (render ".gitignore" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["src/{{sanitized}}/server.clj" (render "clj/server.clj" data)]
             ["src/{{sanitized}}/app.clj" (render "clj/app.clj" data)]
             ["src/{{sanitized}}/middleware/session.clj" (render "clj/middleware/session.clj" data)]
             ["src/{{sanitized}}/middleware/context.clj" (render "clj/middleware/context.clj" data)]
             ["src/{{sanitized}}/util/session.clj" (render "clj/util/session.clj" data)]
             ["src/{{sanitized}}/util/flash.clj" (render "clj/util/flash.clj" data)]
             "src/{{sanitized}}/model/"
             ["src/{{sanitized}}/view/about.clj" (render "clj/view/about.clj" data)]
             ["src/{{sanitized}}/view/auth.clj" (render "clj/view/auth.clj" data)]
             ["src/{{sanitized}}/view/common.clj" (render "clj/view/common.clj" data)]
             ["src/{{sanitized}}/view/home.clj" (render "clj/view/home.clj" data)]
             ["src/{{sanitized}}/view/admin.clj" (render "clj/view/admin.clj" data)]
             ["src/{{sanitized}}/view/profile.clj" (render "clj/view/profile.clj" data)]
             ["src/{{sanitized}}/view/templates/layout.mustache" (render "clj/view/templates/layout.mustache")]
             ["src/{{sanitized}}/view/templates/about.html" (render "clj/view/templates/about.html" data)]
             ["src/{{sanitized}}/view/templates/admin.mustache" (render "clj/view/templates/admin.mustache")]
             ["src/{{sanitized}}/view/templates/auth.mustache" (render "clj/view/templates/auth.mustache")]
             ["src/{{sanitized}}/view/templates/home.mustache" (render "clj/view/templates/home.mustache")]
             ["src/{{sanitized}}/view/templates/profile.mustache" (render "clj/view/templates/profile.mustache")]
             ["resources/public/css/bootstrap.min.css" (render "resources/public/css/bootstrap.min.css")]
             ["resources/public/css/bootstrap-responsive.min.css" (render "resources/public/css/bootstrap-responsive.min.css")]
             ["resources/public/css/jquery-ui.min.css" (render "resources/public/css/jquery-ui.min.css")]
             ["resources/public/css/main.css" (render "resources/public/css/main.css")]
             ["resources/public/img/glyphicons-halflings.png" (render "resources/public/img/glyphicons-halflings.png")]
             ["resources/public/img/glyphicons-halflings-white.png" (render "resources/public/img/glyphicons-halflings-white.png")]
             ["resources/public/js/bootstrap.min.js" (render "resources/public/js/bootstrap.min.js")]
             ["resources/public/js/jquery.min.js" (render "resources/public/js/jquery.min.js")]
             ["resources/public/js/jquery.ui.core.min.js" (render "resources/public/js/jquery.ui.core.min.js")])
    (println "Project" name "successfully generated")))
