//
//  Theme.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import UIKit

class Theme {
    
    static let shared = Theme()
    
    class Font{
        static let CenturyGothic = UIFont.init(name: "CenturyGothic", size: 23)!
        static let Calibri = UIFont.init(name: "Calibri", size: 20)!
    }
    
    class Color{
        
        static let red = UIColor("C30A30")
        static let White = UIColor("F6F7F8")
        static let darkGrey = UIColor("414141")        
    }
    
}


