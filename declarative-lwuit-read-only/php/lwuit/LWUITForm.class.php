<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of LWUITForm
 *
 * @author mahdi
 */
class LWUITForm {

    public function __construct($action, $method='GET') {
        $this->action = $action;
        $this->method = $method;
        $this->components = array();

    }

    public function setTitle($title) {
        $this->title = $title;

    }

    public function addComponent(LWUITComponent $cmp) {
        array_push($this->components, $cmp);
        
    }

    public function setLayout($layout) {
        $this->layout = $layout;

    }
    
    public function toXML() {
        $doc = new DomDocument('1.0');
        $root = $doc->createElement("form");
        $root = $doc->appendChild($root);

        $root->setAttribute("action", $this->action);
        $root->setAttribute("method", $this->method);
        if(!empty($this->title) )
            $root->setAttribute("title", $this->title);

        $root->appendChild($this->layout->toXMLNode($doc, $root));
        
        foreach($this->components as $cmp) {
            $root->appendChild($cmp->toXMLNode($doc, $root));
            
        }
        return $doc->saveXML();
        
    }

    private $action;
    private $method;
    private $title;    
    private $components;
    private $layout;
}
?>
