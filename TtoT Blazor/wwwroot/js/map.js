//ngl, pasted this bad boy from stack overflow but it js connects to leaflet

window.ttotMap = {
    maps: {},

    init: function (elementId, lat, lng, zoom) {
        this.dispose(elementId);

        const map = L.map(elementId).setView([lat, lng], zoom);
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);

        this.maps[elementId] = map;
    },

    dispose: function (elementId) {
        if (this.maps[elementId]) {
            this.maps[elementId].remove();
            delete this.maps[elementId];
        }
    }
};