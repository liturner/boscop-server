import { basemap } from "./basemap.js";
import { AreaStyle, HazardStyle, LineStyle, OptaStyle, setSelectedSource } from "./style.js";

let selected;

let draw;

let boscopProjection = ol.proj.get('http://www.opengis.net/def/crs/EPSG/0/4326')

let boscopNamespace = 'urn:ns:de:turnertech:boscop'

const formatWFS = new ol.format.WFS({
  version: '2.0.0',
  featureNS: boscopNamespace,
  gmlFormat: new ol.format.GML32({
    srsName: boscopProjection.getCode()
  })
});

const lineSource = new ol.source.Vector({
  format: new ol.format.GML32({
    srsName: "EPSG:3857"
  }),
  url: function (extent) {
    return (
      '/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=GetFeature&TYPENAMES=boscop:Line&' +
      'SRSNAME=EPSG:3857'
    );
  },
  strategy: ol.loadingstrategy.bbox
});

lineSource.on("removefeature", function (e) {
  console.log("lineSource - removing feature");
  const node = formatWFS.writeTransaction(null, null, [e.feature], {
    featureNS: boscopNamespace,
    featurePrefix: 'boscop',
    featureType: 'Line',
    srsName: "EPSG:3857",
    version: '2.0.0',
    gmlOptions: {
      featureNS: boscopNamespace,
      featureType: 'Line',
      srsName: "EPSG:3857"
    }
  });
  const xs = new XMLSerializer();
  const payload = xs.serializeToString(node);
  console.log(payload);
  fetch('/ows?SERVICE=WFS&VERSION=2.0.0&REQUEST=Transaction', {
    method: "POST",
    body: payload
  });
});

const lineLayer = new ol.layer.Vector({
  source: lineSource,
  style: LineStyle.styleFunction
});

const copWfsSource = new ol.source.Vector({
  format: formatWFS.gmlFormat_,
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

copWfsSource.on("removefeature", function (e) {
  console.log("areaSource - removing feature");
  const node = formatWFS.writeTransaction(null, null, [e.feature], {
    featureNS: 'urn:ns:de:turnertech:boscop',
    featurePrefix: 'boscop',
    featureType: 'Area',
    srsName: boscopProjection.getCode(),
    version: '2.0.0',
    gmlOptions: {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featureType: 'Area',
      srsName: boscopProjection.getCode()
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

const unitSource = new ol.source.Vector({
  format: formatWFS.gmlFormat_,
  loader: function(extent, resolution, projection, success, failure) {
    const proj = projection.getCode();
    const url = '/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=GetFeature&TYPENAMES=boscop:Unit&' +
                'SRSNAME=' + boscopProjection.getCode() + 
                '&BBOX=' + ol.proj.transformExtent(extent, projection, boscopProjection).join(',') + ',' + boscopProjection.getCode()
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url);
    const onError = function() {
      unitSource.removeLoadedExtent(extent);
      failure();
    }
    xhr.onerror = onError;
    xhr.onload = function() {
      if (xhr.status == 200) {
        const features = unitSource.getFormat().readFeatures(xhr.responseText);
        
        // OpenLayers handles the PointType SRS incorrectly? This is a manual fix
        // to get the correct EPSG:4326 latlon ordering
        features.forEach((feature) => {
          let newCoord = [feature.getGeometry().getFirstCoordinate()[1], feature.getGeometry().getFirstCoordinate()[0]]
          feature.getGeometry().setCoordinates(newCoord, "XY")
          feature.getGeometry().transform(boscopProjection, projection);
        });

        unitSource.addFeatures(features);
        success(features);
      } else {
        onError();
      }
    }
    xhr.send();
  },
  strategy: ol.loadingstrategy.bbox
});

unitSource.on("removefeature", function (e) {
  console.log("unitLayer - removing feature");
});

const hazardSource = new ol.source.Vector({
  format: formatWFS.gmlFormat_,
  loader: function(extent, resolution, projection, success, failure) {
    const proj = projection.getCode();
    const url = '/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=GetFeature&TYPENAMES=boscop:Hazard&' +
                'SRSNAME=' + boscopProjection.getCode() + 
                '&BBOX=' + ol.proj.transformExtent(extent, projection, boscopProjection).join(',') + ',' + boscopProjection.getCode()
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url);
    const onError = function() {
      hazardSource.removeLoadedExtent(extent);
      failure();
    }
    xhr.onerror = onError;
    xhr.onload = function() {
      if (xhr.status == 200) {
        const features = hazardSource.getFormat().readFeatures(xhr.responseText);
        
        // OpenLayers handles the PointType SRS incorrectly? This is a manual fix
        // to get the correct EPSG:4326 latlon ordering
        features.forEach((feature) => {
          let newCoord = [feature.getGeometry().getFirstCoordinate()[1], feature.getGeometry().getFirstCoordinate()[0]]
          feature.getGeometry().setCoordinates(newCoord, "XY")
          feature.getGeometry().transform(boscopProjection, projection);
        });

        hazardSource.addFeatures(features);
        success(features);
      } else {
        onError();
      }
    }
    xhr.send();
  },
  strategy: ol.loadingstrategy.bbox
});

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
    hazardLayer,
    lineLayer
  ],
  view: new ol.View({
    center: [1244800, 6230600],
    zoom: 14
  })
});

const selectSingleClick = new ol.interaction.Select({
  condition: ol.events.condition.click,
  hitTolerance: 10,
  style: null
});
map.addInteraction(selectSingleClick);

function refreshCopLayer() {
  copLayer.getSource().refresh();
  unitLayer.getSource().refresh();
  hazardLayer.getSource().refresh();
  lineLayer.getSource().refresh();
}

function deleteSelected() {
  selected.forEach((selectedElement) => {
    console.log("Removing: " + selectedElement.id_);
    hazardSource.removeFeature(hazardSource.getFeatureById(selectedElement.id_));
    copWfsSource.removeFeature(copWfsSource.getFeatureById(selectedElement.id_));
    lineSource.removeFeature(lineSource.getFeatureById(selectedElement.id_));
  });
  selected = [];
}

/**
 * Resets the state to no input, no drawing etc.
 */
function clearInsertSelection() {
  if(draw) {
    map.removeInteraction(draw);
  }
  document.getElementById('unitSelect').value = ""
  document.getElementById('hazardSelect').value = ""
  document.getElementById('typeSelect').value = ""
}

document.getElementById('typeSelect').addEventListener('change', function () {
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
    e.feature.getGeometry().transform('EPSG:3857', boscopProjection);
    const node = formatWFS.writeTransaction([e.feature], null, null, {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featurePrefix: 'boscop',
      featureType: 'Area',
      srsName: boscopProjection.getCode(),
      version: '2.0.0',
      gmlOptions: {
        featureNS: 'urn:ns:de:turnertech:boscop',
        featureType: 'Area',
        srsName: boscopProjection.getCode()
      }
    });
    e.feature.getGeometry().transform(boscopProjection, 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(() => map.removeInteraction(draw))
    .then(() => clearInsertSelection())
    .then(() => refreshCopLayer());
  });
  map.addInteraction(draw);
});

document.getElementById('hazardSelect').addEventListener('change', function () {
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

    e.feature.getGeometry().transform('EPSG:3857', boscopProjection);
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
    e.feature.getGeometry().transform(boscopProjection, 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(() => map.removeInteraction(draw))
    .then(() => clearInsertSelection())
    .then(() => refreshCopLayer());
  });
  map.addInteraction(draw);
});

document.getElementById('unitSelect').addEventListener('change', function () {
  console.log("Selected Unit")
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
    e.feature.getGeometry().transform('EPSG:3857', boscopProjection);
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
    e.feature.getGeometry().transform(boscopProjection, 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(() => map.removeInteraction(draw))
    .then(() => clearInsertSelection())
    .then(() => refreshCopLayer());
  });
  map.addInteraction(draw);
});

document.getElementById('delete').addEventListener('click', function () {
  if(draw) {
    map.removeInteraction(draw);
  }
  deleteSelected();
});

document.getElementById('addLine').addEventListener('click', function () {
  draw = new ol.interaction.Draw({
    source: lineSource,
    type: 'LineString'
  });
  draw.on('drawend', function (e) {
    //e.feature.setProperties({
    //  "hazardType": hazardSelect.value
    //});
    const node = formatWFS.writeTransaction([e.feature], null, null, {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featurePrefix: 'boscop',
      featureType: 'Line',
      srsName: 'EPSG:3857',
      version: '2.0.0',
      gmlOptions: {
        featureNS: 'urn:ns:de:turnertech:boscop',
        featureType: 'Line',
        srsName: 'EPSG:3857'
      }
    });
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('/ows?SERVICE=WFS&VERSION=2.0.0&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(() => map.removeInteraction(draw))
    .then(() => clearInsertSelection())
    .then(() => refreshCopLayer());
  });
  map.addInteraction(draw);
});

/**
 * Cancel current insert. 
 * TODO: Figure out a better way with only touch.
 */
document.addEventListener("keyup", (event) => {
  if (event.isComposing || event.keyCode === 229) {
    clearInsertSelection();
  }
});

/**
 * Cancel current insert. 
 * TODO: Figure out a better way with only touch.
 */
document.addEventListener("keyup", (event) => {
  if (event.isComposing || event.key == "Delete") {
    deleteSelected();
  }
  map.removeInteraction(draw);
});

selectSingleClick.on('select', function (e) {
  selected = e.selected;
  setSelectedSource(e.selected);
  refreshCopLayer();
});

setInterval(refreshCopLayer, 10000);

clearInsertSelection();
