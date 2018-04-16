//
//  MessageCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import AFDateHelper

class MessageCell: BaseCell {
    
    @IBOutlet weak var vv1 : UIView!
    @IBOutlet weak var text1Lbl : ActiveLabel!
    @IBOutlet weak var date1Lbl : ActiveLabel!
    
    
    @IBOutlet weak var vv2 : UIView!
    @IBOutlet weak var text2Lbl : ActiveLabel!
    @IBOutlet weak var date2Lbl : ActiveLabel!
    
    
    var message : Message!{
        didSet{
            
            if let m = message{
                if !m.to_seller.Boolean{
                    self.vv1.isHidden = false
                    self.vv2.isHidden = true
                    self.text1Lbl.text = m.text
                    
                    if let date = m.created_at{
                        if let d = Date.init(fromString: date, format: DateFormatType.custom("yyyy-MM-dd HH:mm:ss")){
                            self.date1Lbl.text = d.toString(dateStyle: .none, timeStyle: .short, isRelative: false)
                        }
                    }
//                    self.date1Lbl.text = m.created_at
                    
                }else{
                    self.vv2.isHidden = false
                    self.vv1.isHidden = true
                    self.text2Lbl.text = m.text
                    if let date = m.created_at{
                        if let d = Date.init(fromString: date, format: DateFormatType.custom("yyyy-MM-dd HH:mm:ss")){
                            self.date2Lbl.text = d.toString(dateStyle: .none, timeStyle: .short, isRelative: false)
                        }
                    }

//                    self.date2Lbl.text = m.created_at
                }
            }
            
            self.text1Lbl.decideTextDirection()
            self.text2Lbl.decideTextDirection()

        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        self.text1Lbl.enabledTypes = [.url]
        self.text2Lbl.enabledTypes = [.url]
        
        self.text1Lbl.handleURLTap {res  in
            if UIApplication.shared.canOpenURL(res){
                UIApplication.shared.openURL(res)
            }
        }
        self.text2Lbl.handleURLTap {res  in
            if UIApplication.shared.canOpenURL(res){
                UIApplication.shared.openURL(res)
            }
        }
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}

extension UILabel {
    func decideTextDirection () {
        let tagScheme = [NSLinguisticTagScheme.language]
        let tagger    = NSLinguisticTagger(tagSchemes: tagScheme, options: 0)
        tagger.string = self.text
        let lang = tagger.tag(at: 0, scheme: NSLinguisticTagScheme.language,
                              tokenRange: nil, sentenceRange: nil)

        if let kk = lang{
            if kk.rawValue.contains("ar") {
                self.textAlignment = NSTextAlignment.right
            } else {
                self.textAlignment = NSTextAlignment.left
            }
        }
    }
}

