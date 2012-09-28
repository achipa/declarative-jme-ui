<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of LWUITBoxLayout
 *
 * @author mahdi
 */
class LWUITBoxLayout extends LWUITLayout {
    public function __construct($orientation) {
        $this->orientation = $orientation;

    }
    public function toXMLNode(DOMDocument $doc, DOMElement $parent) {
        $box = $doc->createElement("boxlayout");
        $box = $parent->appendChild($box);
        $box->setAttribute("orientation", $this->orientation );

        return $box;
    }

    private $orientation;
    const X_AXIS    = 0;
    const Y_AXIS    = 1;
}
?>
