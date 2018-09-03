////adapted from https://codepen.io/SitePoint/pen/ZbGwqe
(function($) {
  $.fn.fullScreenCarousel = function() {
    this.each(function() {
        var $this = $(this);
        var $item = $this.find('.carousel-item');
        var $wHeight = $(window).height();
        $item.eq(0).addClass('active');
        $item.height($wHeight);
        $item.css({
            'background-size' : 'cover',
            'background-position' : 'center',
            'background-repeat' : 'no-repeat'
        });

        $this.find('img').each(function() {
          var $src = $(this).attr('src');
          var $color = $(this).attr('data-color');
          $(this).parent().css({
            'background-image' : 'url(' + $src + ')',
            'background-color' : $color
          });
          $(this).remove();
        });

        $(window).on('resize', function (){
          $wHeight = $(window).height();
          $item.height($wHeight);
        });

        $this.carousel({
          interval: 6000,
          pause: "false"
        });
    });
    return this;
  };
})(jQuery);

/**
 * Bootstrap Carousel Swipe v1.1
 *
 * jQuery plugin to enable swipe gestures on Bootstrap 3 carousels.
 * Examples and documentation: https://github.com/maaaaark/bcSwipe
 *
 * Licensed under the MIT license.
 */
(function($) {
  $.fn.bcSwipe = function(settings) {
    var config = { threshold: 50 };
    if (settings) {
      $.extend(config, settings);
    }

    this.each(function() {
      var stillMoving = false;
      var start;

      if ('ontouchstart' in document.documentElement) {
        this.addEventListener('touchstart', onTouchStart, false);
      }

      function onTouchStart(e) {
        if (e.touches.length == 1) {
          start = e.touches[0].pageX;
          stillMoving = true;
          this.addEventListener('touchmove', onTouchMove, false);
        }
      }

      function onTouchMove(e) {
        if (stillMoving) {
          var x = e.touches[0].pageX;
          var difference = start - x;
          if (Math.abs(difference) >= config.threshold) {
            cancelTouch();
            if (difference > 0) {
              $(this).carousel('next');
            }
            else {
              $(this).carousel('prev');
            }
          }
        }
      }

      function cancelTouch() {
        this.removeEventListener('touchmove', onTouchMove);
        start = null;
        stillMoving = false;
      }
    });

    return this;
  };
})(jQuery);

$(function(){
    $('.carousel-full-screen').fullScreenCarousel();
    $('.carousel-swipe').bcSwipe({ threshold: 50 });
});