<?php 

/**
 * Dump helper. Functions to dump variables to the screen, in a nicley formatted manner.
 * @author Joost van Veen
 * @version 1.0
 */
if (!function_exists('dump')) {
    function dump ($var, $label = 'Dump', $echo = TRUE)
    {
        // Store dump in variable 
        ob_start();
        var_dump($var);
        $output = ob_get_clean();
        
        // Add formatting
        $output = preg_replace("/\]\=\>\n(\s+)/m", "] => ", $output);
        $output = '<pre style="background: #FFFEEF; color: #000; border: 1px dotted #000; padding: 10px; margin: 10px 0; text-align: left;">' . $label . ' => ' . $output . '</pre>';
        
        // Output
        if ($echo == TRUE) {
            echo $output;
        }
        else {
            return $output;
        }
    }
}

function commercila_status_checkbox($is_active  , $id , $category_id , $position)
{
    if($is_active){
      	$html  =  '<div class="">';
	    $html .=  '<label>';
	    $html .=  '<input id="comm_status_check" comm_id=' .$id. '  position=' .$position. '  onclick="change_status(' .$id. ',' .$category_id. ',' .$position. ',' . 0 . ');"  type="checkbox" class="js-switch" checked></input>';
	    $html .=  '</label>';
	    $html .=  '</div>';	
    }else{
        $html  =  '<div class="">';
	    $html .=  '<label>';
	    $html .=  '<input id="comm_status_check" comm_id=' .$id. ' position=' .$position. ' onclick="change_status(' .$id. ',' .$category_id. ',' .$position. ',' . 1 . ');" type="checkbox" class="js-switch"></input>';
	    $html .=  '</label>';
	    $html .=  '</div>';	
    }   
    return $html;
}

function user_status_checkbox($is_active  , $id)
{
    if($is_active){
      	$html  =  '<div class="">';
	    $html .=  '<label>';
	    $html .=  '<input id="user_status_check" user_id=' .$id. '  onclick="change_user_status(' .$id. ',' .$is_active. ');"  type="checkbox" class="js-switch" checked></input>';
	    $html .=  '</label>';
	    $html .=  '</div>';	
    }else{
     	$html  =  '<div class="">';
	    $html .=  '<label>';
	    $html .=  '<input id="user_status_check" user_id=' .$id. '  onclick="change_user_status(' .$id. ',' .$is_active. ');"  type="checkbox" class="js-switch"></input>';
	    $html .=  '</label>';
	    $html .=  '</div>';	
    }   
    return  $html;
}

function get_sub_cats($cat_id , $lang)
{
   	$CI =& get_instance();
	$CI->load->model('data_sources/categories');
	$subcategoirs = $CI->categories->get_subcats_with_parents($cat_id, $lang);
	return $subcategoirs;
}

function get_cities_array($lang){
 	$CI =& get_instance();
	$CI->load->model('data_sources/locations');
	return $CI->locations->get_cities($lang);
}
