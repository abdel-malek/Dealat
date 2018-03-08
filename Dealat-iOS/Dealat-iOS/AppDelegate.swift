//
//  AppDelegate.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import IQKeyboardManagerSwift

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    
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
        
        
        return true
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
        
    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    
}

