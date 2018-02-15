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

class Provider : BaseManager {
    
    static let shared = Provider()
    static var cats = [Cat]()
    static var isArabic : Bool = false
    
    static func isValidEmail(_ testStr:String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: testStr)
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
            
            print("------\n" + url.absoluteString + "\n------")
            
            img.sd_setImage(with: url, placeholderImage: nil, options: .refreshCached, completed: nil)
            
//            img.kf.setImage(with: url, placeholder: nil, options: nil, progressBlock: nil, completionHandler: nil)
            
        }else{
            img.image = nil
        }
    }
    
    
}

