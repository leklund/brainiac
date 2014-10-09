var Widgets = (function() {
  return {
    ticker: function(div) {
      div.children('.ticker').vTicker({
        showItems: 1
      });
    },
    rssticker: function(div) {
      div.children('.rssticker').vTicker({
        showItems: 1
      });
    },
  };
})();
