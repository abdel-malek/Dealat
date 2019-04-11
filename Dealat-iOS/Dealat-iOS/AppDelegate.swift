//
//  AppDelegate.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import IQKeyboardManagerSwift
import Firebase
import FirebaseMessaging
import UserNotifications
//import Google
import SwiftyJSON
import Fabric
import Crashlytics
import StoreKit
import EggRating

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    let gcmMessageIDKey = "gcm.message_id"
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
                
        
        IQKeyboardManager.shared.enable = true
        
        UINavigationBar.appearance().barTintColor = Theme.Color.red//UIColor.groupTableViewBackground
        UINavigationBar.appearance().tintColor = Theme.Color.White
        UINavigationBar.appearance().titleTextAttributes = [
            NSAttributedString.Key.foregroundColor : Theme.Color.White,
            NSAttributedString.Key.font : Theme.Font.CenturyGothic.withSize(19)
        ]
        UINavigationBar.appearance().barStyle = UIBarStyle.black
        
        //UserDefaults.standard.setValue(["en","ar"], forKey: "AppleLanguages")

        Provider.isArabic = AppDelegate.isArabic()
        
      //  AppDelegate.setupViews() // TODO
        setupNotification(application)
        
        Fabric.with([Crashlytics.self])
        
        
        //TODO IMP
//        EggRating.debugMode = true
//        EggRating.itunesId = "1397149787"

        
        return true
    }
    
    
    func setupNotification(_ application : UIApplication){
        
        //push notification
        if #available(iOS 10.0, *) {
            // For iOS 10 display notification (sent via APNS)
            UNUserNotificationCenter.current().delegate = self
            
            let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
            UNUserNotificationCenter.current().requestAuthorization(
                options: authOptions,
                completionHandler: {_, _ in })
        } else {
            let settings: UIUserNotificationSettings =
                UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
            application.registerUserNotificationSettings(settings)
        }
        
        application.registerForRemoteNotifications()
        
        // [END register_for_notifications]
        
        FirebaseApp.configure()
        
        // [START set_messaging_delegate]
        Messaging.messaging().delegate = self
        // [END set_messaging_delegate]
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(self.tokenRefreshNotification),
                                               name: .InstanceIDTokenRefresh,
                                               object: nil)

        
        
        if let gai = GAI.sharedInstance()  {
            //            assert(false, "Google Analytics not configured correctly")
            //        }
            gai.tracker(withTrackingId: "UA-117516159-1")
            // Optional: automatically report uncaught exceptions.
            gai.trackUncaughtExceptions = true
            // Optional: set Logger to VERBOSE for debug information.
            // Remove before app release.
            gai.logger.logLevel = .verbose;
        }
        
        
    }

    
    
     @objc static func setupViews(){
        
        if let arr =  UserDefaults.standard.value(forKey: "AppleLanguages") as? [String]{
            if let lang = arr.first{
                if lang.contains("ar"){
                    UserDefaults.standard.setValue(["ar"], forKey: "AppleLanguages")
                }else{
                    UserDefaults.standard.setValue(["en"], forKey: "AppleLanguages")
                }
            }
        }
        
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let me = User.getCurrentUser()
        
        if Provider.getLang() == "" {
            let vc = storyboard.instantiateViewController(withIdentifier: "SelectLangVC") as! SelectLangVC
            appDelegate.window?.rootViewController =  vc
        }
        
        else if Provider.getCity() == 0 {
            let vc = storyboard.instantiateViewController(withIdentifier: "SelectLocationVC") as! SelectLocationVC
            appDelegate.window?.rootViewController = vc
        }
            
        else{
            if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
                let vc = storyboard.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
                appDelegate.window?.rootViewController = UINavigationController.init(rootViewController: vc)
                
            }else if me.statues_key == User.USER_STATUES.PENDING_CODE.rawValue{
                let vc = storyboard.instantiateViewController(withIdentifier: "VerificationVC") as! VerificationVC
                appDelegate.window?.rootViewController = UINavigationController.init(rootViewController: vc)

            }else if me.statues_key == User.USER_STATUES.PENDING_PROFILE.rawValue{
                let vc = storyboard.instantiateViewController(withIdentifier: "EditProfileVC") as! EditProfileVC
                vc.fromRegister = true
                appDelegate.window?.rootViewController = UINavigationController.init(rootViewController: vc)
            }else if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
                let vc = storyboard.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                appDelegate.window?.rootViewController = UINavigationController.init(rootViewController: vc)
            }
            
        }
    }
    
    static func isArabic() -> Bool
    {
        if let code =  Locale.preferredLanguages.first{
            if code.contains("ar")
            {
                return true
            }
        }
        return false
    }
        
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable: Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID: \(messageID)")
        }
        print(userInfo)
        
        print("new didReceive 0")

        
        
        if #available(iOS 10, *){

        }
        else{
            
            if application.applicationState == .active{
//                PushManager.handleForegroundNotification(userInfo)
                PushManager.handleNotificationTapping(data: userInfo)
                completionHandler(.newData)
            }else if application.applicationState != .background{
                PushManager.handleNotificationTapping(data: userInfo)
                completionHandler(.newData)
            }else{
                completionHandler(.noData)
            }
        }
        
    }
    
    func application(_ application: UIApplication, didReceive notification: UILocalNotification) {
        
        print("new didReceive 1")
        
        if #available(iOS 10, *) {
            
        }else
        {
            if application.applicationState != UIApplication.State.active{
                PushManager.handleNotificationTapping(data: notification.userInfo)
            }
        }
    }
    
    
    // [START refresh_token]
    @objc func tokenRefreshNotification(_ notification: Notification) {
        print("TOKENTOKEN")
        
        self.refreshToken()
    }
    
    
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("Unable to register for remote notifications: \(error.localizedDescription)")
    }
    
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        
        // With swizzling disabled you must set the APNs token here.
        Messaging.messaging().setAPNSToken(deviceToken, type: MessagingAPNSTokenType.prod)
        
        Messaging.messaging().apnsToken = deviceToken
        
        self.refreshToken()
    }
    
    func refreshToken(){
        
        if let refreshedToken = Messaging.messaging().fcmToken {
            
            Messaging.messaging().subscribe(toTopic: "/topics/all_ios")
            
            if User.isRegistered(){
                if !refreshedToken.isEmpty{
                    Communication.shared.save_user_token(refreshedToken, callback: { (res) in
                        
                    })
                }
            }
        }
    }
    
}

@available(iOS 10, *)
extension AppDelegate : UNUserNotificationCenterDelegate {
    
    // Receive displayed notifications for iOS 10 devices.
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        print("willPresent")
        let userInfo = notification.request.content.userInfo
        
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID userNotificationCenter willPresent: \(messageID)")
        }
        
        print(userInfo)
        
        var isHere = false
        
         let notification = userInfo
            do{
                let not = JSON(notification["ntf_type"])
                let type = not.intValue
                if type == 1{
                    isHere = true
                }
            }catch let err{ print("ERROR: \(err.localizedDescription)")}

        print("isHere :\(isHere) - \(userInfo["gcm.message_id"] as? String)")
        
        if isHere{
            
            if PushManager.LocalSchedules.contains(userInfo["gcm.message_id"] as! String){
                print("CONTAINS ")
                completionHandler([.alert,.badge,.sound])
            }else{
                print("NOT CONTAINS ")
                notific.post(name: "refreshChats".not, object: nil,userInfo : userInfo)
            }
        }else{
            print("ELSEE ")
            completionHandler([.alert,.badge,.sound])
        }
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo
        
        print("new didReceive 2")
        
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID userNotificationCenter didReceive: \(messageID)")
        }
        
        //TODO
//                PushManager.handleNotificationTapping2(userInfo)
        PushManager.handleNotificationTapping(data: userInfo)
        
        print(userInfo)
        
        completionHandler()
    }
}


// [START ios_10_data_message_handling]
extension AppDelegate : MessagingDelegate {
    
    // [START refresh_token]
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String) {
        print("Firebase registration token: \(fcmToken)")
        
        self.refreshToken()
    }
    // [END refresh_token]

    
    // Receive data message on iOS 10 devices while app is in the foreground.
    func messaging(_ messaging: Messaging, didReceive remoteMessage: MessagingRemoteMessage) {
        print("new didReceive 3")

        print("applicationReceivedRemoteMessage10")
        print(remoteMessage.appData)
    }
}
// [END ios_10_data_message_handling]


