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


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    let gcmMessageIDKey = "gcm.message_id"

    
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        IQKeyboardManager.sharedManager().enable = true
        
        UINavigationBar.appearance().barTintColor = Theme.Color.red//UIColor.groupTableViewBackground
        UINavigationBar.appearance().tintColor = Theme.Color.White
        UINavigationBar.appearance().titleTextAttributes = [
            NSAttributedStringKey.foregroundColor : Theme.Color.White,
            NSAttributedStringKey.font : Theme.Font.CenturyGothic
        ]
        UINavigationBar.appearance().barStyle = UIBarStyle.black
        
        //        UserDefaults.standard.setValue(["en","ar"], forKey: "AppleLanguages")
        
                
        Provider.isArabic = AppDelegate.isArabic()
        
        AppDelegate.setupViews()
        setupNotification(application)
        
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
    }

    
    
    static func setupViews(){
        
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
        if Provider.getCity() == 0 {
            /*let vc = storyboard.instantiateViewController(withIdentifier: "SelectLocationVC") as! SelectLocationVC
            
            appDelegate.window?.rootViewController = UINavigationController.init(rootViewController: vc)*/
        }
            
        else{
            
            if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
                let vc = storyboard.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
                appDelegate.window?.rootViewController = UINavigationController.init(rootViewController: vc)
                
            }else if me.statues_key == User.USER_STATUES.PENDING_CODE.rawValue{
                let vc = storyboard.instantiateViewController(withIdentifier: "VerificationVC") as! VerificationVC
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
        
        //notific.post(name: _refreshChats.not, object: nil,userInfo : userInfo)
        
        if #available(iOS 10, *){
            
        }
        else{
            
            if application.applicationState == .active{
                PushManager.handleForegroundNotification(data: userInfo)
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
        if #available(iOS 10, *) {
            
        }else
        {
            if application.applicationState != UIApplicationState.active{
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
        Messaging.messaging().setAPNSToken(deviceToken, type: MessagingAPNSTokenType.sandbox)
        
        Messaging.messaging().apnsToken = deviceToken
        
        self.refreshToken()
    }
    
    func refreshToken(){
        
        if let refreshedToken = Messaging.messaging().fcmToken {
            
//            Messaging.messaging().subscribe(toTopic: "/topics/test")
            
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
        
        completionHandler([.alert, .sound,.badge])
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo
        
        print("didReceive")
        
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID userNotificationCenter didReceive: \(messageID)")
        }
        
        //TODO
        //        PushManager.handleNotificationTapping2(userInfo)
        PushManager.handleNotificationTapping(data: userInfo)
        
        print(userInfo)
        
        completionHandler()
    }
}


// [START ios_10_data_message_handling]
extension AppDelegate : MessagingDelegate {
    
    // [START refresh_token]
    func messaging(_ messaging: Messaging, didRefreshRegistrationToken fcmToken: String) {
        print("Firebase registration token: \(fcmToken)")
        
        self.refreshToken()
    }
    // [END refresh_token]
    
    
    // Receive data message on iOS 10 devices while app is in the foreground.
    func messaging(_ messaging: Messaging, didReceive remoteMessage: MessagingRemoteMessage) {
        print("applicationReceivedRemoteMessage10")
        print(remoteMessage.appData)
    }
}
// [END ios_10_data_message_handling]


