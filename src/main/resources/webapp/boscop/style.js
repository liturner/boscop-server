let styleCache = {};

let selectedThings = []

export function setSelectedSource(source) {
  console.log("set selected source")
  selectedThings = source;
}

/**
 * @deprecated
 */
export class OptaStyle {
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

  static styleFunction(feature) {
    console.log('Styling Opta');
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
}

export class HazardStyle {

  static selectedStyleCache = {};

  static selectedImageSrcMap = {
    "thw-b": './tz/thw-b-selected.png',
    "thw-ztrfu": './tz/thw-ztrfu-selected.png',
  }

  static imageSrcMap = {
    "thw-b": './tz/thw-b.png',
    "thw-ztrfu": './tz/thw-ztrfu.png',
    "thw-helfer": './tz/thw-helfer.png',
    "fw-abc-erkundung": './tz/fw-abc-erkundung.png',
    "hazard-acute": './tz/gefahr-acute.png',
    "gefahr-gs": './tz/gefahr-gs.png',
    "gefahr-vermutet-strom": './tz/gefahr-vermutet-strom.png'
  };

  static optaImageMap = {
    2110: 'thw-ztrfu',
    2200: 'thw-b'
  }

  static selectedImageSrc(hazardType) {
    let imageSrc = HazardStyle.selectedImageSrcMap[hazardType];
    if (!imageSrc) {
      imageSrc = HazardStyle.imageSrc(hazardType);
    }
    return imageSrc;
  }

  static imageSrc(hazardType) {
    let imageSrc = HazardStyle.imageSrcMap[hazardType];
    if (!imageSrc) {
      imageSrc = './tz/' + hazardType + '.png'
    }
    return imageSrc;
  }

  static styleSelectedFunction(feature) {
    console.log('Styling Selected Hazard');

    const hazardType = feature.get('hazardType');
    let style = HazardStyle.selectedStyleCache[hazardType];
    if (!style) {
      style = new ol.style.Style({
        image: new ol.style.Icon({
          src: HazardStyle.selectedImageSrc(hazardType),
          width: 128,
          height: 128
        })
      });
      HazardStyle.selectedStyleCache[hazardType] = style;
    }
    return style;

  }

  static styleFunction(feature) {    
    for(let selectedFeature of selectedThings) {
      console.log(selectedFeature.getId())
      console.log(feature.getId())
      if(selectedFeature.getId() === feature.getId()) {
        return HazardStyle.styleSelectedFunction(feature);
      }
    }

    console.log('Styling Regular Hazard');

    const hazardType = feature.get('hazardType');
    let style = styleCache[hazardType];
    if (!style) {
      style = new ol.style.Style({
        image: new ol.style.Icon({
          src: HazardStyle.imageSrc(hazardType),
          width: 64,
          height: 64
        })
      });
      styleCache[hazardType] = style;
    }
    return style;
  }
}

const patternCanvas = document.createElement('canvas');
const patternContext = patternCanvas.getContext('2d');
 
// Give the pattern a width and height of 50
patternCanvas.width = 50;
patternCanvas.height = 50;
 
// Give the pattern a background color and draw an arc
patternContext.fillStyle = 'rgba(0, 0, 255, 0.05)';
patternContext.fillRect(0, 0, patternCanvas.width, patternCanvas.height);
patternContext.arc(0, 0, 30, 0, .5 * Math.PI);
patternContext.stroke();
 
// Create our primary canvas and fill it with the pattern
const canvas = document.createElement('canvas');
const ctx = canvas.getContext('2d');
const pattern = ctx.createPattern(patternCanvas, 'repeat');


export class AreaStyle {
  static styleCache = {};

  static imageSrc(opta) {
    return 'todo';
  }

  static styleFunction(feature) {
    console.log('Styling Area');
    return new ol.style.Style({
      //Width of the stroke
      stroke: new ol.style.Stroke({
        color: [0, 0, 255, 1],
        width: 3
      }),
      //Fill polygon with canvas pattern
      fill: new ol.style.Fill({
        // color: [255, 0, 51, 0.1]
        color: pattern
      })
    })
  };
}