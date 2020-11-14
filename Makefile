node_modules: package.json
	npm install
	touch $@

release: node_modules
	export ENV=dev; \
	shadow-cljs release app

copy-res: release
	rsync -avu public/ cordova/www

cordova: copy-res
	cd cordova && \
	cordova build electron --release

.PHONY : clean
clean:
	rm -rf .shadow-cljs/builds/app/