<?php
require_once '/lwuit/init.php';

header("Content-Type: text/xml");

define('ROOT', "http://localhost/lwuit/");

$form = new LWUITForm(ROOT . 'text_fields.php');
$form->setTitle("My First Form");

$lbl = new LWUITButton();
$lbl->setText("my label");
$form->addComponent($lbl);

echo $form->toXML();