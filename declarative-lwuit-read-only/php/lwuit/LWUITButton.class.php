<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of LWUITButton
 *
 * @author mahdi
 */
class LWUITButton extends LWUITLabel{
    public function __construct() {
        parent::__construct();
    }

    public function toXMLNode(DOMDocument $doc, DOMElement $parent) {
        $lbl = $doc->createElement("button");
        $lbl = $parent->appendChild($lbl);

        if(!empty($this->text) )
            $lbl->setAttribute("text", $this->text );

        return $lbl;

    }  
}
?>
