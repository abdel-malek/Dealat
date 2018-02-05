<?php

/**
 * Description of FCM
 *
 * @author Amal Abdulraouf
 */
class NotificationHelper {

    function __construct() {
        
    }
    public function send_notification_to_device($registatoin_ids, $message, $data, $os, $title , $type) {
        $message_object = array(
            'title' => $title, 
            "ntf_text" => $message,
            "ntf_body" => $data,
            'ntf_type'=>$type
        );
        $url = 'https://fcm.googleapis.com/fcm/send';
        if (strtolower($os) == "ios")
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $message_object,
                'content_available' => true,
                'priority' => 'high',
                'notification' => array('sound' => 'default', 'badge' => 0, 'body' => $message)
            );
        else 
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $message_object
            );

        $headers = array(
            'Authorization: key=' . "AAAA0utiXdM:APA91bE7DOPC2Nq8D7Hs5HoM5i3MRrpnO37jPxCgbMiiwcUqsGBYZMQxAd6ElQ4ee_NSqrnSUToxLp5Bq5I_BiIRdgeo7hE3v-138SkhQgKTWm77a2EgTd3Hlg-Spia6e-heVjxMOclz",
            'Content-Type: application/json'
        );
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        // Disabling SSL Certificate support temporarly
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

        // Execute post
        $result = curl_exec($ch);
	
        $res = json_decode($result);
	 //   dump($res);
        // Close connection
        curl_close($ch);
        
        if (!$res->success) {
            return 0;
        } else {
            return 1;
        }
    }

}

?>
