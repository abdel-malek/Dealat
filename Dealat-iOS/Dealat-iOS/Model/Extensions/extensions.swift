//
//  extensions.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import SwiftyJSON
import UIKit


extension String{
    
    // to get notification center Name
    var not : NSNotification.Name {
        return NSNotification.Name.init(rawValue: self)
    }
    
    var getData : Data {
        if self != ""{
            return self.data(using: String.Encoding.utf8)!
        }else{
            return Data()
        }
    }
    
    var localized : String{
        
        if let arr =  UserDefaults.standard.value(forKey: "AppleLanguages") as? [String]{
            if let lang = arr.first{
                var lang2 = lang
                if lang != "ar" && lang != "en"{
                    print("OKK \(lang)")
                    lang2 = "en"
                }
                
                if self == "S.P"{
//                    print("LOCALIZED S.P")
                    return (lang2 == "ar") ? Provider.shared.currency_ar : Provider.shared.currency_en
                }
                if self == "Salary"{
                    var t = (lang2 == "ar") ? "الراتب" : "Salary"
                    let l = (lang2 == "ar") ? Provider.shared.currency_ar : Provider.shared.currency_en
                    if !l.isEmpty{
                        t += " (\(l))"
                    }
                    return t
                }
                if self == "Price SP"{
                    var t = (lang2 == "ar") ? "السعر" : "Price"
                    let l = (lang2 == "ar") ? Provider.shared.currency_ar : Provider.shared.currency_en
                    if !l.isEmpty{
                        t += " (\(l))"
                    }
                    return t
                }
                
                let path  = Bundle.main.path(forResource: lang2, ofType: "lproj")
                let bundle = Bundle.init(path: path!)
                let s = bundle!.localizedString(forKey: self, value: nil, table: nil)
                return s
            }
        }

        return "TEST";
//        return NSLocalizedString(self, comment: "")
    }
    
    
    
    
    var localized_currancy : String{
        return String(format: NSLocalizedString("%@ Price", comment: ""), "\(self)")
    }

    
    // to get string after convert from html
    //    var html2String: String {
    //        return html2AttributedString?.string ?? ""
    //    }
    //    var html2AttributedString: NSAttributedString? {
    //        guard let data = data(using: .utf8) else { return nil }
    //        do {
    //            return try NSAttributedString(data: data, options: [NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType, NSCharacterEncodingDocumentAttribute: String.Encoding.utf8.rawValue], documentAttributes: nil)
    //        } catch let error as NSError {
    //            print(error.localizedDescription)
    //            return  nil
    //        }
    //    }
    
    func convertToDictionary() -> [String: Any]? {
        if let data = self.data(using: .utf8) {
            do {
                return try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any]
            } catch {
                print(error.localizedDescription)
            }
        }
        return nil
    }
    
}

extension UIImage {
    func resized(withPercentage percentage: CGFloat) -> UIImage? {
        let canvasSize = CGSize(width: size.width * percentage, height: size.height * percentage)
        UIGraphicsBeginImageContextWithOptions(canvasSize, false, scale)
        defer { UIGraphicsEndImageContext() }
        draw(in: CGRect(origin: .zero, size: canvasSize))
        return UIGraphicsGetImageFromCurrentImageContext()
    }
    func resized(toWidth width: CGFloat) -> UIImage? {
        let canvasSize = CGSize(width: width, height: CGFloat(ceil(width/size.width * size.height)))
        UIGraphicsBeginImageContextWithOptions(canvasSize, false, scale)
        defer { UIGraphicsEndImageContext() }
        draw(in: CGRect(origin: .zero, size: canvasSize))
        return UIGraphicsGetImageFromCurrentImageContext()
    }
    
    convenience init(view: UIView) {
        
        UIGraphicsBeginImageContextWithOptions(view.bounds.size, false, 0.0)
        //        UIColor.white.setFill()
        view.drawHierarchy(in: view.bounds, afterScreenUpdates: false)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        self.init(cgImage: (image?.cgImage)!)
    }
    
}



extension UIColor{
    
    public convenience init(_ hex : String) {
        var cString:String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        
        if (cString.hasPrefix("#")) {
            cString.remove(at: cString.startIndex)
        }
        
        if ((cString.characters.count) != 6) {
            self.init(red: 255/255, green: 0, blue: 0, alpha: 1)// UIColor.gray
            return
        }
        
        var rgbValue:UInt32 = 0
        Scanner(string: cString).scanHexInt32(&rgbValue)
        
        self.init(
            red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }
    
    static func random() -> UIColor {
        return UIColor(red:   .random(),
                       green: .random(),
                       blue:  .random(),
                       alpha: 1.0)
    }
    
}

extension CGFloat {
    static func random() -> CGFloat {
        return CGFloat(arc4random()) / CGFloat(UInt32.max)
    }
}


extension UIView {
    
    @IBInspectable var cornerRadius: CGFloat {
        get {
            return layer.cornerRadius
        }
        set {
            layer.cornerRadius = newValue
            layer.masksToBounds = newValue > 0
        }
    }
    
    @IBInspectable var borderWidth: CGFloat {
        get {
            return layer.borderWidth
        }
        set {
            layer.borderWidth = newValue
        }
    }
    
    @IBInspectable var borderColor: UIColor? {
        get {
            return UIColor.init(cgColor: layer.borderColor!)
        }
        set {
            layer.borderColor = newValue?.cgColor
        }
    }
}

extension UITextField{
    
    @IBInspectable var placeHolderColor: UIColor? {
        get {
            return self.placeHolderColor
        }
        set {
            if let ph = newValue{
                let p = self.placeholder != nil ? self.placeholder! : ""
                self.attributedPlaceholder = NSAttributedString(string:p, attributes:[NSAttributedString.Key.foregroundColor: ph])
            }
        }
    }
}

extension UISearchBar {
    
    func change(_ textFont : UIFont?) {
        
        for view : UIView in (self.subviews[0]).subviews {
            
            if let textField = view as? UITextField {
                textField.font = textFont
            }
        }
    }
}


extension Double{
    
    func formatDigital() -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.maximumFractionDigits = 0;
        formatter.locale = Locale(identifier: Locale.current.identifier)
        let result = formatter.string(from: self as NSNumber);
        return result!;
    }
    
}

extension String {
    
    // formatting text for currency textField
    func currencyInputFormatting() -> String {
        
        var number: NSNumber!
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
//        formatter.maximumFractionDigits = 0;
//        formatter.numberStyle = .currencyAccounting
//        formatter.currencySymbol = "$"
//        formatter.maximumFractionDigits = 5
//        formatter.minimumFractionDigits = 5
        
        var amountWithPrefix = self
        
        // remove from String: "$", ".", ","
        let regex = try! NSRegularExpression(pattern: "[^0-9]", options: .caseInsensitive)
        amountWithPrefix = regex.stringByReplacingMatches(in: amountWithPrefix, options: NSRegularExpression.MatchingOptions(rawValue: 0), range: NSMakeRange(0, self.count), withTemplate: "")
        
        let double = (amountWithPrefix as NSString).doubleValue
        number = NSNumber(value: (double)) //NSNumber(value: (double / 100))
        
        // if first number is 0 or all numbers were deleted
        guard number != 0 as NSNumber else {
            return ""
        }
        
        return formatter.string(from: number)!
    }
    
    func deleteDecimal() -> String{
        var string = self.replacingOccurrences(of: ",", with: "")
        string = string.replacingOccurrences(of: ".", with: "")

        return string
    }
    
    
}


extension JSON{
    
    var Boolean : Bool{
        if self.boolValue || self.intValue == 1{
            return true
        }else{
            return false
        }
    }
}



extension UIViewController{
    
    func showAlert_notification(_ data: [AnyHashable: Any]?)
    {
        let view = "View".localized
        let cancel = "OK".localized
        var publicNot : Bool = false
        
        if let notification = data {
            if let aps = notification["aps"] as? [String: AnyObject]
            {
                print("APS")
                if let msg = aps["alert"] as? [String: AnyObject]
                {
                    
                    do{
                        let not = JSON(notification["ntf_type"])
                        let type = not.intValue
                        
                        if type == 3{
                            publicNot = true
                        }
                    }catch let err{ print("ERROR: \(err.localizedDescription)")}

                    
                    let title = ((msg["title"] as? String) != nil) ? msg["title"] as! String : "Dealat"
                    let body = ((msg["body"] as? String) != nil) ? msg["body"] as! String : ""

                    print("MSG")
                    
                    let alert = UIAlertController(title: title, message: body.emojiUnescapedString, preferredStyle: .alert)
                    
                    
                    alert.addAction(UIAlertAction(title: cancel, style: .cancel, handler: nil))
                    
                    if !publicNot {
                        alert.addAction(UIAlertAction(title: view, style: .default, handler: { (ac) in
                            PushManager.handleNotificationTapping2(data: data)
                        }))
                    }
                    
                    
                    self.present(alert, animated: true, completion: nil)
                }
            }
        }
    }

    
}


extension Date {
    /// Returns the amount of years from another date
    func years(from date: Date) -> Int {
        return Calendar.current.dateComponents([.year], from: date, to: self).year ?? 0
    }
    /// Returns the amount of months from another date
    func months(from date: Date) -> Int {
        return Calendar.current.dateComponents([.month], from: date, to: self).month ?? 0
    }
    /// Returns the amount of weeks from another date
    func weeks(from date: Date) -> Int {
        return Calendar.current.dateComponents([.weekOfMonth], from: date, to: self).weekOfMonth ?? 0
    }
    /// Returns the amount of days from another date
    func days(from date: Date) -> Int {
        return Calendar.current.dateComponents([.day], from: date, to: self).day ?? 0
    }
    /// Returns the amount of hours from another date
    func hours(from date: Date) -> Int {
        return Calendar.current.dateComponents([.hour], from: date, to: self).hour ?? 0
    }
    /// Returns the amount of minutes from another date
    func minutes(from date: Date) -> Int {
        return Calendar.current.dateComponents([.minute], from: date, to: self).minute ?? 0
    }
    /// Returns the amount of seconds from another date
    func seconds(from date: Date) -> Int {
        return Calendar.current.dateComponents([.second], from: date, to: self).second ?? 0
    }
    /// Returns the amount of nanoseconds from another date
    func nanoseconds(from date: Date) -> Int {
        return Calendar.current.dateComponents([.nanosecond], from: date, to: self).nanosecond ?? 0
    }
    /// Returns the a custom time interval description from another date
    func offset(from date: Date) -> String {
        if years(from: date)   > 0 { return "\(years(from: date))y"   }
        if months(from: date)  > 0 { return "\(months(from: date))M"  }
        if weeks(from: date)   > 0 { return "\(weeks(from: date))w"   }
        if days(from: date)    > 0 { return "\(days(from: date))d"    }
        if hours(from: date)   > 0 { return "\(hours(from: date))h"   }
        if minutes(from: date) > 0 { return "\(minutes(from: date))m" }
        if seconds(from: date) > 0 { return "\(seconds(from: date))s" }
        if nanoseconds(from: date) > 0 { return "\(nanoseconds(from: date))ns" }
        return ""
    }
}


//     to get string after convert from html
extension Data {
    var html2AttributedString: NSAttributedString? {
        do {
            return try NSAttributedString(data: self, options: [.documentType: NSAttributedString.DocumentType.html, .characterEncoding: String.Encoding.utf8.rawValue], documentAttributes: nil)
        } catch {
            print("error:", error)
            return  nil
        }
    }
    var html2String: String {
        return html2AttributedString?.string ?? ""
    }
}

extension String {
    var html2AttributedString: NSAttributedString? {
        return Data(utf8).html2AttributedString
    }
    var html2String: String {
        return html2AttributedString?.string ?? ""
    }
}


extension UITableView{
    
    func EmptyMessage(message : String = "No search results".localized) {
        let rect = CGRect.init(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height)
        let messageLabel = UILabel(frame: rect)
        messageLabel.text = message
        messageLabel.textColor = UIColor.black
        messageLabel.numberOfLines = 0;
        messageLabel.textAlignment = .center;
        messageLabel.font = Theme.Font.Calibri
        messageLabel.sizeToFit()
        
        self.backgroundView = messageLabel;
        self.separatorStyle = .none;
    }
}

extension UICollectionView{
    
    func EmptyMessage(message : String = "No search results".localized) {
        let rect = CGRect.init(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height)
        let messageLabel = UILabel(frame: rect)
        messageLabel.text = message
        messageLabel.textColor = UIColor.black
        messageLabel.numberOfLines = 0;
        messageLabel.textAlignment = .center;
        messageLabel.font = Theme.Font.Calibri
        messageLabel.sizeToFit()
        
        self.backgroundView = messageLabel;
    }
}
