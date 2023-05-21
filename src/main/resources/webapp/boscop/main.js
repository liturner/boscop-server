import { basemap } from "./basemap.js";
import { AreaStyle, HazardStyle, OptaStyle } from "./style.js";

const formatWFS = new ol.format.WFS({
  version: '2.0.0',
  featureNS: 'urn:ns:de:turnertech:boscop'
});

const copWfsSource = new ol.source.Vector({
  format: new ol.format.GML32({
    srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
  }),
  url: function (extent) {
    return (
      '/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=GetFeature&TYPENAMES=boscop:Area&' +
      'SRSNAME=http://www.opengis.net/def/crs/EPSG/0/4326&BBOX=' +
      ol.proj.transformExtent(extent, 'EPSG:3857','EPSG:4326').join(',') +
      ',http://www.opengis.net/def/crs/EPSG/0/4326'
    );
  },
  strategy: ol.loadingstrategy.bbox
});

const unitSource = new ol.source.Vector({
  format: new ol.format.GML32({
    srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
  }),
  url: function (extent) {
    return (
      '/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=GetFeature&TYPENAMES=boscop:Unit&' +
      'SRSNAME=http://www.opengis.net/def/crs/EPSG/0/4326&BBOX=' +
      ol.proj.transformExtent(extent, 'EPSG:3857','EPSG:4326').join(',') +
      ',http://www.opengis.net/def/crs/EPSG/0/4326'
    );
  },
  strategy: ol.loadingstrategy.bbox
});

unitSource.on("removefeature", function (e) {
  console.log("unitLayer - removing feature");
});

const hazardSource = new ol.source.Vector({
  format: new ol.format.GML32({
    srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
  }),
  url: function (extent) {
    return (
      '/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=GetFeature&TYPENAMES=boscop:Hazard&' +
      'SRSNAME=http://www.opengis.net/def/crs/EPSG/0/4326&BBOX=' +
      ol.proj.transformExtent(extent, 'EPSG:3857','EPSG:4326').join(',') +
      ',http://www.opengis.net/def/crs/EPSG/0/4326'
    );
  },
  strategy: ol.loadingstrategy.bbox
});

// TODO: Remove from WFS
hazardSource.on("removefeature", function (e) {
  console.log("hazardSource - removing feature");
  const node = formatWFS.writeTransaction(null, null, [e.feature], {
    featureNS: 'urn:ns:de:turnertech:boscop',
    featurePrefix: 'boscop',
    featureType: 'Hazard',
    srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326',
    version: '2.0.0',
    gmlOptions: {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featureType: 'Hazard',
      srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
    }
  });
  const xs = new XMLSerializer();
  const payload = xs.serializeToString(node);
  console.log(payload);
  fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
    method: "POST",
    body: payload
  });
});

const copLayer = new ol.layer.Vector({
  source: copWfsSource,
  style: AreaStyle.styleFunction
});

const unitLayer = new ol.layer.Vector({
  source: unitSource,
  style: OptaStyle.styleFunction
});

const hazardLayer = new ol.layer.Vector({
  source: hazardSource,
  style: HazardStyle.styleFunction
});

const map = new ol.Map({
  controls: ol.control.defaults.defaults().extend([new ol.control.FullScreen(), new ol.control.ScaleLine()]),
  target: 'map',
  layers: [
    basemap,
    copLayer,
    unitLayer,
    hazardLayer
  ],
  view: new ol.View({
    center: [1244800, 6230600],
    zoom: 14
  })
});

const selectSingleClick = new ol.interaction.Select({
  condition: ol.events.condition.click
});
map.addInteraction(selectSingleClick);

function refreshCopLayer() {
  copLayer.getSource().refresh();
  unitLayer.getSource().refresh();
  hazardLayer.getSource().refresh();
}

let draw;
document.getElementById('insert').addEventListener('click', function () {
  if(draw) {
    map.removeInteraction(draw);
  }
  draw = new ol.interaction.Draw({
    source: copWfsSource,
    type: 'Polygon'
  });
  draw.on('drawend', function (e) {
    e.feature.setProperties({
      "areaType": typeSelect.value
    });
    e.feature.getGeometry().transform('EPSG:3857', 'EPSG:4326');
    const node = formatWFS.writeTransaction([e.feature], null, null, {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featurePrefix: 'boscop',
      featureType: 'Area',
      srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326',
      version: '2.0.0',
      gmlOptions: {
        featureNS: 'urn:ns:de:turnertech:boscop',
        featureType: 'Area',
        srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
      }
    });
    e.feature.getGeometry().transform('EPSG:4326', 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(text => map.removeInteraction(draw));
  });
  map.addInteraction(draw);
});


document.getElementById('insertHazard').addEventListener('click', function () {
  if(draw) {
    map.removeInteraction(draw);
  }
  draw = new ol.interaction.Draw({
    source: hazardSource,
    type: 'Point'
  });
  draw.on('drawend', function (e) {
    e.feature.setProperties({
      "hazardType": hazardSelect.value
    });
    e.feature.getGeometry().transform('EPSG:3857', 'EPSG:4326');
    const node = formatWFS.writeTransaction([e.feature], null, null, {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featurePrefix: 'boscop',
      featureType: 'Hazard',
      srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326',
      version: '2.0.0',
      gmlOptions: {
        featureNS: 'urn:ns:de:turnertech:boscop',
        featureType: 'Hazard',
        srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
      }
    });
    e.feature.getGeometry().transform('EPSG:4326', 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(text => map.removeInteraction(draw));
  });
  map.addInteraction(draw);
});

document.getElementById('insertUnit').addEventListener('click', function () {
  if(draw) {
    map.removeInteraction(draw);
  }
  draw = new ol.interaction.Draw({
    source: hazardSource,
    type: 'Point'
  });
  draw.on('drawend', function (e) {
    e.feature.setProperties({
      "hazardType": unitSelect.value
    });
    e.feature.getGeometry().transform('EPSG:3857', 'EPSG:4326');
    const node = formatWFS.writeTransaction([e.feature], null, null, {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featurePrefix: 'boscop',
      featureType: 'Hazard',
      srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326',
      version: '2.0.0',
      gmlOptions: {
        featureNS: 'urn:ns:de:turnertech:boscop',
        featureType: 'Hazard',
        srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
      }
    });
    e.feature.getGeometry().transform('EPSG:4326', 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(text => map.removeInteraction(draw));
  });
  map.addInteraction(draw);
});

/**
 * Cancel current insert. 
 * TODO: Figure out a better way with only touch.
 */
document.addEventListener("keyup", (event) => {
  if (event.isComposing || event.keyCode === 229) {
    return;
  }
  map.removeInteraction(draw);
});

/**
 * Cancel current insert. 
 * TODO: Figure out a better way with only touch.
 */
document.addEventListener("keyup", (event) => {
  if (event.isComposing || event.key == "Delete") {
    console.log("Delete Pressed");
    selected.forEach((selectedElement) => {
      //sourve.remove -> will then trigger 
      console.log("Removing: " + selectedElement.id_);
      hazardSource.removeFeature(selectedElement);
    });
    
    return;
  }
  map.removeInteraction(draw);
});

let selected;

selectSingleClick.on('select', function (e) {
  selected = e.selected;
});

//setInterval(refreshCopLayer, 10000);