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
        return NSLocalizedString(self, comment: "")
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
                self.attributedPlaceholder = NSAttributedString(string:p, attributes:[NSAttributedStringKey.foregroundColor: ph])
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

extension JSON{
    
    var Boolean : Bool{
        if self.boolValue || self.intValue == 1{
            return true
        }else{
            return false
        }
    }
}


