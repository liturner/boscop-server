import { Map, View } from 'ol';
import GeoJSON from 'ol/format/GeoJSON.js';
import TileLayer from 'ol/layer/Tile.js';
import VectorLayer from 'ol/layer/Vector.js';
import { get as getProjection } from 'ol/proj.js';
import VectorSource from 'ol/source/Vector.js';
import XYZ from 'ol/source/XYZ.js';
import { Circle, Fill, Style } from 'ol/style.js';
import TileGrid from 'ol/tilegrid/TileGrid.js';
import './style.css';

const projection = getProjection('EPSG:3857');

const myStyle = new Style({
  image: new Circle({
    fill: new Fill({color: 'red'}),
    radius: 5,
  }),
});

const map = new Map({
  target: 'map',
  layers: [
    //new TileLayer({
    //  source: new OSM()
    //}),
    new TileLayer({
      opacity: 1,
      source: new XYZ({
        attributions:
          '<a href="https://www.bkg.bund.de">Kartendarstellung: © Bundesamt für Kartographie und Geodäsie (2021)</a>, ' +
          '<a href="https://sg.geodatenzentrum.de/web_public/Datenquellen_TopPlus_Open.pdf">Datenquellen</a>',
        url: 'https://mapproxy.turnertech.de/tms/1.0.0/top_plus_open/webmercator/{z}/{x}/{-y}.png',
        projection: projection,
        tileGrid: new TileGrid ({
          extent: [-20037508.34, -20037508.34, 20037508.34, 20037508.34],
          resolutions: [156543.033928041, 78271.5169640205, 39135.7584820102, 19567.8792410051, 9783.93962050256, 4891.96981025128, 2445.98490512564, 1222.99245256282, 611.496226281410, 305.748113140705, 152.874056570353, 76.4370282851763, 38.2185141425881, 19.1092570712941, 9.55462853564703, 4.77731426782352, 2.38865713391176, 1.19432856695588]
        })
      }),
    }),
    new VectorLayer({
      source: new VectorSource({
        url: 'https://cop.turnertech.de',
        format: new GeoJSON(),
      }),
      style: myStyle
    })
  ],
  view: new View({
    center: [1000000, 6650300],
    zoom: 6
  })
});
