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
//import SwiftyJSON

/*class PushManager{
    
    static let LN_KEY = "mobifone"
    
    static var token : String?
    static var storyBoard = UIStoryboard(name: "Main", bundle: nil)
    
    static func handleForegroundNotification(data: [AnyHashable: Any]?) {
        
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
    }
    
    static func handleNotificationTapping(data: [AnyHashable: Any]?)
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
                    let id = JSON(notification["ntf_body"]).intValue
                    self.OpenRequest(id: id)
                    
                }else if type == 2{
                    let id = JSON(notification["ntf_body"]).intValue
                    self.OpenOrder(id: id)
                }
            }catch let err{ print("ERROR: \(err.localizedDescription)")}
        }
    }
    
    static func OpenOrder(id : Int)
    {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let order = Order()
        order.id = JSON(id)
        
        if let navigationController = appDelegate.window?.rootViewController as?  UINavigationController
        {
            let vc = self.storyBoard.instantiateViewController(withIdentifier: "OrderDetailsVC") as! OrderDetailsVC
            vc.type = 1
            vc.order = order
            
            navigationController.pushViewController(vc, animated: true)
        }
    }
    
    static func OpenRequest(id : Int)
    {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let req = Req()
        req.id = JSON(id)
        
        if let navigationController = appDelegate.window?.rootViewController as?  UINavigationController
        {
            let vc = self.storyBoard.instantiateViewController(withIdentifier: "RequestDetailsVC") as! RequestDetailsVC
            vc.type = 1
            vc.req = req
            
            navigationController.pushViewController(vc, animated: true)
        }
    }
    
}*/
