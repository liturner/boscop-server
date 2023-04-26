export class OptaStyle {
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
    if (!imageSrc) {
      imageSrc = 'tz/thw-helfer.png'
    }
    return imageSrc;
  }
}

const styleCache = {};
export const styleFunction = function (feature) {
  console.log(feature.getProperties())
  const opta = feature.get('opta');
  let style = styleCache[opta];
  if (!style) {
    if (!opta) {
      style = new ol.layer.Vector().getStyleFunction()();
    } else {
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
    }
    styleCache[opta] = style;
  }
  return style;
};