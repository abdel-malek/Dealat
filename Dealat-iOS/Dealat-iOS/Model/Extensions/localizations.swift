//
//  localizations.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import UIKit

extension UITextField {
    
    @IBInspectable var localizedPlaceholder: String! {
        get { return self.placeholder }
        set {
            self.placeholder = NSLocalizedString(newValue, comment: "")
        }
    }
    
    @IBInspectable var localizedText: String! {
        get { return self.text }
        set {
            self.text = NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UITextView {
    
    @IBInspectable var localizedText: String! {
        get { return self.text }
        set {
            self.text = NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UIBarItem {
    
    @IBInspectable var localizedTitle: String! {
        get { return self.title }
        set {
            self.title = NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UILabel {
    
    @IBInspectable var localizedText: String! {
        get { return self.text }
        set {
            self.text = NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UINavigationItem {
    
    @IBInspectable var localizedTitle: String! {
        get { return self.title }
        set {
            self.title = NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UIButton {
    
    @IBInspectable var localizedTitle: String! {
        get { return self.titleLabel?.text }
        set {
            self.setTitle(NSLocalizedString(newValue, comment: ""), for: .normal)
        }
    }
    
}

extension UISearchBar {
    
    @IBInspectable var localizedPrompt: String! {
        get { return self.prompt }
        set {
            self.prompt = NSLocalizedString(newValue, comment: "")
        }
    }
    
    @IBInspectable var localizedPlaceholder: String! {
        get { return self.placeholder }
        set {
            self.placeholder = NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UISegmentedControl {
    
    @IBInspectable var localized: Bool {
        get { return true }
        set {
            for index in 0..<numberOfSegments {
                let title = NSLocalizedString(titleForSegment(at: index)!, comment: "")
                self.setTitle(title, forSegmentAt: index)
            }
        }
    }
}
