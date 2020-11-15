electron-main-res := cordova/resource/electron/cdv-electron-main.js
electron-main := cordova/platforms/electron/platform_www/cdv-electron-main.js

node_modules: package.json
	npm install
	touch $@

release: node_modules
	export ENV=dev; \
	shadow-cljs release app

sync-www: release
	rsync -avu public/ cordova/www

cordova/node_modules: cordova/package.json
	cd cordova && \
	npm install
	touch $@

cordova/platforms/electron:
	cd cordova && \
	cordova platform add electron
	touch $@

electron: cordova/node_modules cordova/platforms/electron sync-www
	cp $(electron-main-res) $(electron-main)
	cd cordova && \
	cordova build electron --release

.PHONY : clean
clean:
	rm -rf .shadow-cljs/builds/app/
	cd cordova && \
	rm -rf node_modules/ && \
	rm -rf platforms/ && \
	rm -rf plugins/