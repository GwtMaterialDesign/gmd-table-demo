var cacheName = 'cache_1613005166376';

var filesToCache = [  
'/gmd-table-demo/',

'GmdDataTableDemo/68217242FF55870C898FAC2EDEE6C843.cache.js',

'GmdDataTableDemo/9420A40C44F65881F80D460A7321FEDE.cache.js',

'GmdDataTableDemo/DEE1F4CADB3785A78432638C2BB7FCF6.cache.js',

'GmdDataTableDemo/GmdDataTableDemo.devmode.js',

'GmdDataTableDemo/GmdDataTableDemo.nocache.js',

'GmdDataTableDemo/clear.cache.gif',

'GmdDataTableDemo/css/animation.css',

'GmdDataTableDemo/css/animation.min.css',

'GmdDataTableDemo/css/fontawesome-all.css',

'GmdDataTableDemo/css/fontawesome-all.min.css',

'GmdDataTableDemo/css/material-icons.css',

'GmdDataTableDemo/css/material-icons.min.css',

'GmdDataTableDemo/css/materialize.blue.css',

'GmdDataTableDemo/css/materialize.blue.min.css',

'GmdDataTableDemo/css/materialize.css',

'GmdDataTableDemo/css/materialize.min.css',

'GmdDataTableDemo/font/material-icons/MaterialIcons-Regular.eot',

'GmdDataTableDemo/font/material-icons/MaterialIcons-Regular.ttf',

'GmdDataTableDemo/font/material-icons/MaterialIcons-Regular.woff',

'GmdDataTableDemo/font/material-icons/MaterialIcons-Regular.woff2',

'GmdDataTableDemo/fontawesome-icons/fa-brands-400.eot',

'GmdDataTableDemo/fontawesome-icons/fa-brands-400.ttf',

'GmdDataTableDemo/fontawesome-icons/fa-brands-400.woff',

'GmdDataTableDemo/fontawesome-icons/fa-brands-400.woff2',

'GmdDataTableDemo/fontawesome-icons/fa-regular-400.eot',

'GmdDataTableDemo/fontawesome-icons/fa-regular-400.ttf',

'GmdDataTableDemo/fontawesome-icons/fa-regular-400.woff',

'GmdDataTableDemo/fontawesome-icons/fa-regular-400.woff2',

'GmdDataTableDemo/fontawesome-icons/fa-solid-900.eot',

'GmdDataTableDemo/fontawesome-icons/fa-solid-900.ttf',

'GmdDataTableDemo/fontawesome-icons/fa-solid-900.woff',

'GmdDataTableDemo/fontawesome-icons/fa-solid-900.woff2',

'index.html',

'launcher-icons/launcher1x.png',

'launcher-icons/launcher2x.png',

'launcher-icons/launcher4x.png',

'launcher-icons/launcher5x.png',

'splash/font/Roboto-Regular.eot',

'splash/font/Roboto-Regular.ttf',

'splash/font/Roboto-Regular.woff',

'splash/font/Roboto-Regular.woff2',

'splash/splash.css',

'splash/splash.js'
    ];


/**
 * The install event is your chance to cache everything you need before being able to control clients. The promise you
 * pass to event.waitUntil() lets the browser know when your install completes, and if it was successful.
 */
self.addEventListener('install', e => {
    e.waitUntil(
        caches.open(cacheName).then(cache => {
            return cache.addAll(filesToCache)
                .then(() => self.skipWaiting());
        })
    );
});

/**
 * Once your service worker is ready to control clients and handle functional events like push and sync, you'll get an
 * activate event. But that doesn't mean the page that called .register() will be controlled.
 */
self.addEventListener('activate', event => {
    event.waitUntil(
        caches.keys().then(function(cacheNames) {
            return Promise.all(
                cacheNames.map(function(oldCache) {
                    if (oldCache !== cacheName) {
                        console.log('ServiceWorker : Deleting old cache:', oldCache);
                        return caches.delete(oldCache);
                    }
                })
            );
        }).then(function() {
            console.log('ServiceWorker : Claiming clients for version', cacheName);
            return self.clients.claim();
        })
    );
});

/**
 * It contains information about the fetch, including the request and how the receiver will treat the response.
 * It provides the event.respondWith() method, which allows us to provide a response to this fetch.
 */
self.addEventListener('fetch', event => {
    if (event.request.method !== 'GET') {
        /* If we don't block the event as shown below, then the request will go to
           the network as usual.
        */
        return;
    }
    event.respondWith(
        caches.open(cacheName)
            .then(cache => cache.match(event.request, {ignoreSearch: true}))
            .then(response => {
                return response || fetch(event.request);
            })
    );
});