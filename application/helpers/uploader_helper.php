<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

function upload_attachement($controller, $path, $new_name=null) {
    $data = array();
    foreach ($_FILES as $index => $value) {
        if ($value['name'] != '') {
        	if($new_name == null){
        		$new_name =  time();
        	}
            $controller->load->library('upload');
            $controller->upload->initialize(set_upload_options($path ,$new_name));
            //upload the doc
            if (!$controller->upload->do_upload($index)) {
                $error = $controller->upload->display_errors();
                throw new parent_exception($error);
            } else {
                $data[$index] = array('upload_data' => $controller->upload->data());
            }
        }
    }
    return $data;
}

function set_upload_options($path , $new_name) {
    $config = array();
    $config['upload_path'] = dirname($_SERVER["SCRIPT_FILENAME"]) . "/" . $path;
    $config['allowed_types'] = 'jpeg|png|jpg|gif';
	$config['file_name'] = $new_name;
    ini_set("upload_max_filesize", '20M');
    return $config;
}
