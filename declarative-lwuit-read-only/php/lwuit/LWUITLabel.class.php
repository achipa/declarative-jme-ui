<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of LWUITLabel
 *
 * @author mahdi
 */
class LWUITLabel extends LWUITComponent {
    public function __construct() {
        parent::__construct();
    }

    public function setText($text) {
        $this->text = $text;

    }

    public function toXMLNode(DOMDocument $doc, DOMElement $parent) {
        $lbl = $doc->createElement("label");
        $lbl = $parent->appendChild($lbl);

        if(!empty($this->text) )
            $lbl->setAttribute("text", $this->text );

        return $lbl;

    }
    
    protected $text;
}
?>
