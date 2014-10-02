var Slideshow = (function () {
  var template;

  return {
    initialize: function () {
      console.log("slideshow init");
      template = $('#slideshow-template');
      Updater.addListener("slideshow", this.render);
    },

    render: function(e, payload) {
      var content = $.mustache(template.html(), payload);
      $("div#slideshow").html(content);
      $('.banner').unslider();
    }
  };
})();


$(function () {
  Slideshow.initialize();
});

//$(function() {
      //$('.banner').unslider({
        //keys: false,
        //dots: false,
        //fluid: false
      //});
//});
