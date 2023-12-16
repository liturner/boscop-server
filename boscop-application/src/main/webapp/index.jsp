<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" type="image/x-icon" href="favicon.ico" />
    <link rel="stylesheet" href="bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="boscop/style.css">
    <title>BOSCOP</title>
  </head>
  <body>
    <nav>
      <ul>
        <li><a class="active" href="/">Map</a></li>
        <li><a href="tracker">Trackers</a></li>
        <li><a href="api">API</a></li>
      </ul>
    </nav>
      <div id="headerMenu" class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
        <div class="btn-group me-2" role="group" aria-label="Button group with nested dropdown">            
          <div class="input-group">
            <label class="input-group-text" for="typeSelect">Area:</label>
            <select class="form-select" id="typeSelect">
              <option value="flooded-area">Flooded Area</option>
              <option value="Square">Brand</option>
              <option value="Box">Brand Fortwickelnd</option>
              <option value="Star">ABC</option>
              <option value="None">None</option>
            </select>
          </div>
        </div>
        <div class="btn-group me-2" role="group" aria-label="Button group with nested dropdown">        
          <div class="input-group">
            <label class="input-group-text" for="hazardSelect">Gefahr:</label>
            <select class="form-select" id="hazardSelect">
              <option value="hazard-acute">Gefahr Akute</option>
              <option value="gefahr-gs">Gefahr Stoff</option>
              <option value="gefahr-vermutet-strom">Gefahr Vermutet Strom</option>
              <option value="maßnahme">Maßnahme</option>
              <option value="schadenstelle-1">Schadenstelle 1</option>
              <option value="schadenstelle-2">Schadenstelle 2</option>
              <option value="schadenstelle-3">Schadenstelle 3</option>
              <option value="schadenstelle-4">Schadenstelle 4</option>
              <option value="schadenstelle-5">Schadenstelle 5</option>
            </select>
          </div>
        </div>
        <div class="btn-group me-2" role="group" aria-label="Button group with nested dropdown">        
          <div class="input-group">
            <label class="input-group-text" for="unitSelect">Unit:</label>
            <select class="form-select" id="unitSelect">
              <option value="meldekopf">Meldekopf</option>
              <option value="versorgungsstelle-verpflegung">Verpflegung</option>
              <option value="el">EL</option>
              <option value="eal">EAL</option>
              <option value="thw-ztrfu">THW ZTrFü</option>
              <option value="thw-helfer">THW Helfer</option>
              <option value="thw-b">THW Bergungsgruppe</option>
              <option value="thw-fgr-n">THW FGr N</option>
              <option value="thw-fgr-wb">THW FGr W(B)</option>
              <option value="thw-ztr">THW ZTr</option>
              <option value="thw-gkw-1">THW GKW I</option>
              <option value="thw-lkw-lkr">THW LKW Lkr</option>
              <option value="thw-mlw-1">THW MLW I</option>
              <option value="thw-mlw-4">THW MLW IV</option>
              <option value="thw-mtw">THW MTW</option>
              <option value="thw-pkw-g">THW PKW (G)</option>
              <option value="thw-mzpt">THW MzPt</option>
              <option value="thw-mzab">THW MzAB</option>
              <option value="thw-mzb">THW MzB</option>
              <option value="thw-anhänger">THW Anhänger</option>
              <option value="thw-anhänger-nea-50kva-lima">THW NEA 50kVA LiMa</option>
              <option value="fw-abc-erkundung">Fw ABC Erkundung</option>
            </select>
          </div>
        </div>
        <button id="delete" type="button" class="btn btn-danger">Delete</button>
        <button id="addLine" type="button" class="btn btn-primary">Line</button>
      </div>
      <div id="map"></div>
    <script src="./ol/ol.js"></script>
    <script type="module" src="./boscop/main.js"></script>
    <footer></footer>
  </body>
</html>
