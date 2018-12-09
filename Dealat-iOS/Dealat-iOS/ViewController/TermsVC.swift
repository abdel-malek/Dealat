//
//  TermsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/26/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import WebKit

class TermsVC: BaseVC,WKNavigationDelegate {

    @IBOutlet weak var textView : UITextView!
    var webView : WKWebView!

    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.title = "termsMessage3".localized
//        self.textView.isHidden = true
        
        self.navigationItem.leftBarButtonItem = UIBarButtonItem.init(barButtonSystemItem: UIBarButtonItem.SystemItem.stop, target: self, action: #selector(self.dis))
        
        getData()
    }
    
    override func getRefreshing() {
        Communication.shared.get_about_info { (res) in
            self.hideLoading()
            
                do{
                    let d = res.terms.data(using: String.Encoding.utf8)!
                    let y = try NSAttributedString.init(data: d, options: [.documentType: NSAttributedString.DocumentType.html,.characterEncoding : String.Encoding.utf8.rawValue], documentAttributes: nil)
                    
                    let dir = AppDelegate.isArabic() ? [NSWritingDirection.rightToLeft.rawValue] : [NSWritingDirection.leftToRight.rawValue]
                    let att = NSMutableAttributedString.init(attributedString: y)
                    att.addAttributes([NSAttributedString.Key.foregroundColor: UIColor.white,NSAttributedString.Key.font: Theme.Font.Calibri, NSAttributedString.Key.writingDirection : dir], range: NSRange(location: 0, length: att.length))
                    
                    self.textView.attributedText = att
                }catch let err{
                    print("ERR : \(err.localizedDescription)")
                }

            
//            let htmlString = "<html>\(res.terms)</html>"
//            var v = (self.navigationController?.navigationBar.frame.height)!
//            v += UIApplication.shared.statusBarFrame.size.height
//            self.webView = WKWebView(frame: self.view.bounds)
//            self.webView.frame.size.height -= v
//            self.webView?.sizeToFit()
//            self.webView?.backgroundColor = .clear
//            self.webView.isOpaque = false
//            self.webView?.scrollView.bouncesZoom = false
//            self.webView?.scrollView.delegate = self
//            self.webView?.navigationDelegate = self
//            self.webView.loadHTMLString(htmlString, baseURL: nil)
//            self.view.addSubview(self.webView!)
        }
    }

    @objc func dis(){
        self.dismiss(animated: true, completion: nil)
    }
    

}
