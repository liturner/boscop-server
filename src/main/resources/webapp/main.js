class OptaStyle {
  constructor(opta) {
    this.opta = opta;
  }

  static ortsverband(opta) {
    return opta.substring(8, 12);
  }

  static imageSrc(opta) {
    const imageSrcMap = {
      2110: 'tz/thw-ztrfu.png',
      2200: 'tz/thw-b.png'
    };

    const id = opta.substring(13, 17);
    let imageSrc = imageSrcMap[id];
    if(!imageSrc) {
      imageSrc = 'tz/thw-helfer.png'
    }
    return imageSrc;
  }
}

const styleCache = {};
const styleFunction = function(feature) {
  const opta = feature.get('OPTA');
  let style = styleCache[opta];
  if (!style) {
    style = new ol.style.Style({
      image: new ol.style.Icon({
        src: OptaStyle.imageSrc(opta),
        width: 64,
        height: 64,
      }),
      text: new ol.style.Text({
        text: OptaStyle.ortsverband(opta),
        textBaseline: 'top',
        offsetY: 16,
        font: 'bold 14px Calibri,sans-serif',
        fill: new ol.style.Fill({
          color: 'black',
        }),
        stroke: new ol.style.Stroke({
          color: 'white',
          width: 2,
        }),
      }),
    });
    styleCache[opta] = style;
  }
  return style;
};

const copLayer = new ol.layer.Vector({
  source: new ol.source.Vector({
    url: '/cop',
    format: new ol.format.GeoJSON(),
  }),
  style: styleFunction
});

const map = new ol.Map({
  target: 'map',
  layers: [
    //new TileLayer({
    //  source: new OSM()
    //}),
    new ol.layer.Tile({
      opacity: 1,
      source: new ol.source.XYZ({
        attributions:
          '<a href="https://www.bkg.bund.de">Kartendarstellung: © Bundesamt für Kartographie und Geodäsie (2021)</a>, ' +
          '<a href="https://sg.geodatenzentrum.de/web_public/Datenquellen_TopPlus_Open.pdf">Datenquellen</a>',
        url: 'https://mapproxy.turnertech.de/tms/1.0.0/top_plus_open/webmercator/{z}/{x}/{-y}.png',
        //projection: ol.proj.proj4.fromEPSGCode(3857),
        tileGrid: new ol.tilegrid.TileGrid ({
          extent: [-20037508.34, -20037508.34, 20037508.34, 20037508.34],
          resolutions: [156543.033928041, 78271.5169640205, 39135.7584820102, 19567.8792410051, 9783.93962050256, 4891.96981025128, 2445.98490512564, 1222.99245256282, 611.496226281410, 305.748113140705, 152.874056570353, 76.4370282851763, 38.2185141425881, 19.1092570712941, 9.55462853564703, 4.77731426782352, 2.38865713391176, 1.19432856695588, 0.597164283477939]
        })
      }),
    }),
    copLayer
  ],
  view: new ol.View({
    center: [1000000, 6650300],
    zoom: 6
  })
});

function refreshCopLayer() {
  copLayer.getSource().refresh();
}

setInterval(refreshCopLayer, 5000);