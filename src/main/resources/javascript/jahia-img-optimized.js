document.addEventListener("DOMContentLoaded", function(event) {

    console.log('Jahia module images optimized started');

    const JahiaImageOptimized = {

        Optimization: {

            init: function () {

                let targetImages = document.querySelectorAll("img.jahia-optimized");

                targetImages.forEach(function(image) {

                    let imgClientWidth = image.clientWidth;
                    let imgWidthCeil = Math.ceil(imgClientWidth/5)*5;
                    let imgPath = image.getAttribute('data-path');

                    if (imgWidthCeil >= 100) {
                        let imageSrc = window.location.origin + '/modules/ImageSecureUrl?path='+ imgPath  + '&size=' + imgWidthCeil + '&convert=' + canUseWebP;
                        image.setAttribute('data-src', imageSrc);
                    } else {
                        image.setAttribute('data-src', imgPath);
                    }

                    image.classList.add('lazyload');

                    // Update src with image path on error
                    image.addEventListener('error', function(){
                        image.setAttribute('src', imgPath);
                    });

                });

                let doit;
                // Listen for resize changes
                window.addEventListener("resize", function() {
                    clearTimeout(doit);
                    doit = setTimeout(JahiaImageOptimized.Optimization.init(), 2000);
                }, false);

            },

        },

        Tools: {

            canUseWebP: function() {

                let elem = document.createElement('canvas');
                if (!!(elem.getContext && elem.getContext('2d'))) {
                    // was able or not to get WebP representation
                    return elem.toDataURL('image/webp').indexOf('data:image/webp') === 0;
                }
                // very old browser like IE 8, canvas not supported
                return false;

            }

        }

    };

    const canUseWebP = JahiaImageOptimized.Tools.canUseWebP();
    JahiaImageOptimized.Optimization.init();
    
});
