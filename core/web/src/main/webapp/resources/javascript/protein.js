/**
 * Behaviour for protein.jsp
 *
 * @author  Antony Quinn
 * @version $Id$
 */

// Prepare protein match popup
// E.g. Tie "match-location-1" span to "match-popup-1" hidden div content.
function preparePopup(spanId) {
    var searchString = 'location-';
    var prefix = spanId.substring(0, spanId.indexOf(searchString));
    var postfix = spanId.substring(prefix.length + searchString.length, spanId.length);
    var popupId = prefix.concat('popup-', postfix);
    //alert('spanId: '.concat(spanId, ', prefix: ', prefix, ', postfix: ', postfix, ', popupId: ', popupId));

    $('#'.concat(spanId)).qtip({
        content: {
            text: $('#'.concat(popupId)),
            title: {
                text: 'Location data',
                button: true // Close button
            }
        },
        position: {
            my: 'top center',
            at: 'bottom center',
            viewport: $(window), // Keep the tooltip on-screen at all times
            effect: true // Positioning animation
        },
        show: {
            event: 'click',
            solo: false // Show one tooltip at a time?
        },
        hide: 'close',
        style: {
            classes: 'ui-tooltip-wiki ui-tooltip-light ui-tooltip-shadow'
        }
    });
}

// Disables or enables stylesheets for database colouring
function configureStylesheets(disable) {
    $.cookie("colour-by-domain", disable, { path: '/' });
    $("link.database").each(function(i) {
        this.disabled = disable;
    });
}

function displayType(checkbox) {
    var checked = checkbox.checked;
    var classes = checkbox.value;
    $.cookie('#' + checkbox.id, checked, { path: '/' });
    // May be more than one class controlled by this checkbox, white space separated.
    var cssClasses = classes.split(" ");
    for (i = 0; i < cssClasses.length; i++) {
        var typeSelector = '.' + cssClasses[i];
        if (checked) {
            $(typeSelector).show("blind", { direction: "vertical" }, 300);
        }
        else {
            $(typeSelector).hide("blind", { direction: "vertical" }, 300);
        }
    }
}

function displayUnintegrated(checkbox) {
    var checked = checkbox.checked;
    $.cookie('#' + checkbox.id, checked, { path: '/' });
    if (checkbox.checked) {
        $('#unintegrated').show("blind", { direction: "vertical" }, 300);
    }
    else {
        $('#unintegrated').hide("blind", { direction: "vertical" }, 300);
    }
}