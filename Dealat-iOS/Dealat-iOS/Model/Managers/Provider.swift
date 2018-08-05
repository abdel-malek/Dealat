//
//  Provider.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import UIKit
import SDWebImage
import Kingfisher
import Google
import AFDateHelper

class Provider : BaseManager {
    
    static let shared = Provider()
    static var isArabic : Bool = false
    static let PAGE_SIZE = 8
    
    var cats = [Cat]()
    var catsFull = [Cat]()

    var currency_en : String = ""
    var currency_ar : String = ""
    
//    static var searchText : String!
    static var filter = FilterParams()
    
    static var selectedCategory : Cat!
    static var selectedLocation : Location!
    
    static var logoImage = #imageLiteral(resourceName: "Dealat logo red")
  
    static func loadAllChildren(_ res : [Cat] , i : Int) -> [Cat]{
        let catsBases = res.filter({$0.parent_id.intValue == i})
        var cats = [Cat]()
        
        for j in catsBases{
            cats.append(j)
            cats += Provider.loadAllChildren(res, i : j.category_id.intValue)
        }
        
        return cats
    }
    

    static func isValidEmail(_ testStr:String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: testStr)
    }
    
    static func goToHome(){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
        let nv = UINavigationController.init(rootViewController: vc)
        
        appDelegate.window?.rootViewController = nv
    }
    
    static func setCity(_ city_id : Int){
        UserDefaults.standard.set(city_id, forKey: "city_id")
    }
    static func getCity() -> Int{
        if let city_id = UserDefaults.standard.value(forKey: "city_id") as? Int{
            return city_id
        }
        return 0
    }
    
    
    static func setLang(_ lang : String){
        UserDefaults.standard.set(lang, forKey: "lang_first")
    }
    static func getLang() -> String{
        if let lang = UserDefaults.standard.value(forKey: "lang_first") as? String{
            return lang
        }
        return ""
    }

    
    static func getEnglishNumber(_ NumberStr : String) -> String{
        let Formatter: NumberFormatter = NumberFormatter()
        Formatter.locale = Locale(identifier: "EN")
        
        if let final = Formatter.number(from: NumberStr), final != 0{
            return String(describing: final)
        }else{
            return NumberStr
        }
    }
    
    
    static func getAttribute(text : String, font : UIFont, color : UIColor) -> NSAttributedString{
        
        let p = NSMutableParagraphStyle()
        p.alignment = .center
        //        p.paragraphSpacingBefore = 30
        p.paragraphSpacing = 10
        
        let attributedString = NSAttributedString(string: text, attributes: [
            NSAttributedStringKey.font : font,
            NSAttributedStringKey.foregroundColor : color,
            NSAttributedStringKey.paragraphStyle : p
            ])
        
        return attributedString
    }
    
    static func sd_setImage(_ img : UIImageView, urlString : String!){
        
        if let u = urlString,
        let us = u.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlPathAllowed),
        let url = URL.init(string: Communication.shared.baseImgsURL + us){
            
//            print("------\n" + url.absoluteString + "\n------")
            img.sd_setShowActivityIndicatorView(true)
            img.sd_setIndicatorStyle(.gray)

            img.sd_setImage(with: url, placeholderImage: nil, options: .refreshCached, completed: nil)
            
//            img.kf.setImage(with: url, placeholder: nil, options: nil, progressBlock: nil, completionHandler: nil)
            
        }else{
            img.image = nil
        }
    }
    
    // TO DO 1.1
    // set time for activate wating minutes
    static func addTimer(_ minutes : Int) {
        let calendar = Calendar.current
        let date = calendar.date(byAdding: .minute, value: minutes, to: Date())
        UserDefaults.standard.set(date, forKey: "timerActive")
    }
    
    static func getTimer() -> Int{
        if let m = UserDefaults.standard.value(forKey: "timerActive"){
            if let d = m as? Date{
                
                let s = d.seconds(from: Date())
//                let s = Date().secondsEarlier(than: d)
                
                if s < 0 {
                    return 0
                }
                
                return s
            }
        }
        
        return 0
    }

    
    static func setScreenName(_ name : String){
        if let tracker = GAI.sharedInstance().defaultTracker{
            tracker.set(kGAIScreenName, value: name)
            
            if let builder = GAIDictionaryBuilder.createScreenView(){
                tracker.send(builder.build() as! [AnyHashable : Any])
            }
        }
    }
    
    
}

