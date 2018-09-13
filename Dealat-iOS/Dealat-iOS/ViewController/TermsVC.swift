//
//  TermsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/26/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class TermsVC: BaseVC {

    @IBOutlet weak var textView : UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.title = "termsMessage3".localized
        
        self.navigationItem.leftBarButtonItem = UIBarButtonItem.init(barButtonSystemItem: UIBarButtonSystemItem.stop, target: self, action: #selector(self.dis))
        
        getData()
    }
    
    override func getRefreshing() {
        Communication.shared.get_about_info { (res) in
            self.hideLoading()
            
            self.textView.text = res.terms
            
//            DispatchQueue.main.async {
//                self.textView.attributedText = res.terms.html2AttributedString
//            }

        }
    }

    @objc func dis(){
        self.dismiss(animated: true, completion: nil)
    }
    

}
