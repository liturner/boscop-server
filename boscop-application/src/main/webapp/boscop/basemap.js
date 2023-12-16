export const basemap = new ol.layer.Tile({
  opacity: 1,
  source: new ol.source.XYZ({
    attributions:
      '<a href="https://www.bkg.bund.de">Kartendarstellung: © Bundesamt für Kartographie und Geodäsie (2021)</a>, ' +
      '<a href="https://sg.geodatenzentrum.de/web_public/Datenquellen_TopPlus_Open.pdf">Datenquellen</a>',
    url: 'https://mapproxy.turnertech.de/tms/1.0.0/top_plus_open/webmercator/{z}/{x}/{-y}.png',
    //projection: ol.proj.proj4.fromEPSGCode(3857),
    tileGrid: new ol.tilegrid.TileGrid({
      extent: [-20037508.34, -20037508.34, 20037508.34, 20037508.34],
      resolutions: [156543.033928041, 78271.5169640205, 39135.7584820102, 19567.8792410051, 9783.93962050256, 4891.96981025128, 2445.98490512564, 1222.99245256282, 611.496226281410, 305.748113140705, 152.874056570353, 76.4370282851763, 38.2185141425881, 19.1092570712941, 9.55462853564703, 4.77731426782352, 2.38865713391176, 1.19432856695588, 0.597164283477939]
    })
  }),
});

export const onlineBasemap = new ol.layer.Tile({
  opacity: 0.7,
  source: new ol.source.WMTS({
    attributions:
      '<a href="https://www.bkg.bund.de">Kartendarstellung: © Bundesamt für Kartographie und Geodäsie (2021)</a>, ' +
      '<a href="https://sg.geodatenzentrum.de/web_public/Datenquellen_TopPlus_Open.pdf">Datenquellen</a>',
    url: 'https://sgx.geodatenzentrum.de/wmts_topplus_open/tile/1.0.0/web/default/WEBMERCATOR/{TileMatrix}/{TileRow}/{TileCol}.png',
    requestEncoding: 'REST',
    format: 'image/png',
    tileGrid: new ol.tilegrid.WMTS({
      extent: [-20037508.34, -20037508.34, 20037508.34, 20037508.34],
      resolutions: [156543.033928041, 78271.5169640205, 39135.7584820102, 19567.8792410051, 9783.93962050256, 4891.96981025128, 2445.98490512564, 1222.99245256282, 611.496226281410, 305.748113140705, 152.874056570353, 76.4370282851763, 38.2185141425881, 19.1092570712941, 9.55462853564703, 4.77731426782352, 2.38865713391176, 1.19432856695588, 0.597164283477939],
      matrixIds: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19],
    }),
    wrapX: true,
  }),
})