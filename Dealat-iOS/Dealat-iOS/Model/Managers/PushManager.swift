//
//  PushManager.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import AVFoundation
import LNNotificationsUI
import SwiftyJSON

class PushManager{
    
    static let LN_KEY = "Dealat"
    
    static var LocalSchedules = [String]()
    
    static var token : String?
    static var storyBoard = UIStoryboard(name: "Main", bundle: nil)
    
    /*static func handleForegroundNotification(data: [AnyHashable: Any]?) {
        
        if let notification = data {
            
            // handle JSON response
            if let aps = notification["aps"] as? [String: AnyObject]
            {
                if let msg = aps["alert"] as? String
                {
                    
                    let pushTopPresentationsView = LNNotification(message: msg)
                    pushTopPresentationsView?.icon = #imageLiteral(resourceName: "logoMzn")
                    pushTopPresentationsView?.defaultAction = LNNotificationAction(title: "", handler:{ (action) in
                        self.handleNotificationTapping(data: data)
                    })
                    
                    LNNotificationCenter.default().present(pushTopPresentationsView, forApplicationIdentifier: LN_KEY)
                    
                    
                    // YAHYA
                    let notification : UILocalNotification = UILocalNotification()
                    notification.alertBody =  msg
                    notification.userInfo = data;
                    notification.soundName = UILocalNotificationDefaultSoundName
                    notification.fireDate = Date()
                    UIApplication.shared.scheduleLocalNotification(notification)
                    
                    var soundId: SystemSoundID = 0
                    let bundle = Bundle.main
                    guard let soundUrl = bundle.url(forResource: "sms", withExtension: "wav") else {
                        return
                    }
                    AudioServicesCreateSystemSoundID(soundUrl as CFURL, &soundId)
                    AudioServicesPlayAlertSound(soundId)
                }
            }
        }
    }*/
    
    static func handleNotificationTapping2(data: [AnyHashable: Any]?)
    {
        print("handleNotificationTapping")
        
        UIApplication.shared.applicationIconBadgeNumber = 0
        
        
        if let notification = data {
            print("handleNotificationTapping: \(notification)")
            
            do{
                let not = JSON(notification["ntf_type"])
                
                print("TTT : \(not.intValue)")
                
                let type = not.intValue
                
                print("TYPE : \(type)")
                
                if type == 1{
                    print("BODY : \(notification["ntf_body"])")
                    
                    if let payload = notification["ntf_body"]
                    {
                        print("ntf_body: \(payload)")
                        
                        let bod = JSON(payload)
                        do{
                            let dd = bod.stringValue.data(using: String.Encoding.utf8)
                            let ii = try JSONSerialization.jsonObject(with: dd!, options: JSONSerialization.ReadingOptions.mutableLeaves)
                            let tt  = JSON(ii)
                            
                            if let obj = tt.dictionaryObject{
                                if let chat = Chat(JSON: obj){
                                    self.OpenChat(chat: chat)
                                }
                            }
                        }catch let err{
                            print("ERROR")
                            print(err.localizedDescription)
                        }
                    }
        
                }else if type == 2{
                    
                    if let payload = notification["ntf_body"]
                    {
                        print("ntf_body: \(payload)")
                        
                        let bod = JSON(payload)
                        do{
                            let dd = bod.stringValue.data(using: String.Encoding.utf8)
                            let ii = try JSONSerialization.jsonObject(with: dd!, options: JSONSerialization.ReadingOptions.mutableLeaves)
                            let tt  = JSON(ii)
                            
                            if let obj = tt.dictionaryObject{
                                if let ad = AD(JSON: obj){
                                    self.OpenADD(ad: ad)
                                }
                            }
                        }catch let err{
                            print("ERROR")
                            print(err.localizedDescription)
                        }
                    }


                    
                }
            }catch let err{ print("ERROR: \(err.localizedDescription)")}
        }
    }
    
    static func OpenADD(ad : AD)
    {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        
        if let navigationController = appDelegate.window?.rootViewController as?  UINavigationController
        {
            let vc = self.storyBoard.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
            vc.tamplateId = ad.tamplate_id.intValue
            vc.ad = ad
            
            navigationController.pushViewController(vc, animated: true)
        }
    }
    
    static func OpenChat(chat : Chat)
    {
        print("OpenChat")
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        if let navigationController = appDelegate.window?.rootViewController as?  UINavigationController
        {
            let vc = self.storyBoard.instantiateViewController(withIdentifier: "ChatDetailsVC") as! ChatDetailsVC
            vc.chat = chat
            
            navigationController.pushViewController(vc, animated: true)
        }
    }
    
    
    static func handleForegroundNotification(_ data: [AnyHashable: Any]?) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        if let vc = appDelegate.window?.rootViewController{
            vc.showAlert_notification(data)
        }
        
        if let notification = data {
            if let aps = notification["aps"] as? [String: AnyObject]
            {
                if let msg = aps["alert"] as? [String: AnyObject]
                {
                    
                    let title = ((msg["title"] as? String) != nil) ? msg["title"] as! String : "Dealat"
                    let body = ((msg["body"] as? String) != nil) ? msg["body"] as! String : ""

                
                    let not : UILocalNotification = UILocalNotification()
                    not.alertTitle = title
                    not.alertBody =  body
                    not.userInfo = data;
                    not.soundName = UILocalNotificationDefaultSoundName
                    not.fireDate = Date()
                    UIApplication.shared.scheduleLocalNotification(not)
                    
                    var soundId: SystemSoundID = 0
                    let bundle = Bundle.main
                    guard let soundUrl = bundle.url(forResource: "sms", withExtension: "wav") else{
                        return
                    }
                    AudioServicesCreateSystemSoundID(soundUrl as CFURL, &soundId)
                    AudioServicesPlayAlertSound(soundId)
                }
            }
        }
    }
    
    static func handleNotificationTapping( data: [AnyHashable: Any]?){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        if let vc = appDelegate.window?.rootViewController{
            vc.showAlert_notification(data)
            print("OKK")
        }
        
        print("false")

    }

    
}
