var Slideshow = (function () {
  var template;

  return {
    initialize: function () {
      template = $('#instagram-tag-template');
      Updater.addListener("instagram-tag", this.render);
    },

    render: function(e, payload) {
      var content = $.mustache(template.html(), payload);
      $("div#instagram-tag").html(content);
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
