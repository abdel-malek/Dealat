//
//  AdsListVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import CHIPageControl

class AdsListVC: BaseVC, UISearchBarDelegate {
    
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var collectionView2 : UICollectionView!
    @IBOutlet weak var pageControl : CHIPageControlAji!
    var searchBar:UISearchBar = UISearchBar(frame: CGRect.init(x : 0,y : 0,width : 200,height : 20))
    
    
    @IBOutlet weak var viewBtn: UIBarButtonItem!
    
    var x1 = -1
    var timer : Timer = Timer()
    var viewsType : Int = 1
    
    var commericals : [String]{
        var ar = [String]()
        ar.append("ad_image_39")
        ar.append("ad_image_40")
        ar.append("ad_image_42")
        ar.append("ad_image_43")
        return ar
    }
    
    var cat = Cat()
    var ads : [AD] = [AD]()
    /*{
     var arr = [AD]()
     arr.append(AD(JSON: ["main_image" : "ad1"])!)
     arr.append(AD(JSON: ["main_image" : "ad2"])!)
     arr.append(AD(JSON: ["main_image" : "ad3"])!)
     arr.append(AD(JSON: ["main_image" : "ad4"])!)
     arr.append(AD(JSON: ["main_image" : "ad5"])!)
     arr.append(AD(JSON: ["main_image" : "ad6"])!)
     arr.append(AD(JSON: ["main_image" : "ad7"])!)
     arr.append(AD(JSON: ["main_image" : "ad8"])!)
     arr.append(AD(JSON: ["main_image" : "ad9"])!)
     return arr
     }*/
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
    }
    
    override func getRefreshing() {
        Communication.shared.get_ads_by_main_category(self.cat.category_id.intValue) { (res) in
            self.hideLoading()
            self.ads = res
            self.collectionView2.reloadData()
        }
    }
    
    override func setupViews() {
        // CollectionView
        collectionView.dataSource = self
        collectionView.delegate = self
        collectionView2.dataSource = self
        collectionView2.delegate = self
        
        //PageControl
        pageControl.numberOfPages = self.commericals.count
        self.timer.invalidate()
        self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
        self.timer.fire()
        
        // Search bar
        if #available(iOS 11.0, *) {
            //searchBar.heightAnchor.constraint(equalToConstant: 44).isActive = true
        }
        
        self.searchBar.placeholder =  "Search".localized
        self.searchBar.change(Theme.Font.Calibri)
        self.searchBar.sizeToFit()
        self.navigationItem.titleView = searchBar
        self.searchBar.delegate = self
        
        
    }
    
    
    @IBAction func changeView(_ sender  : UIBarButtonItem){
        self.viewsType = (viewsType == 1) ? 2 : (viewsType == 2) ? 3 : 1
        
        viewBtn.image = UIImage.init(named: "views-\(viewsType)")
        
        UIView.transition(with: self.collectionView2, duration: 0.3, options: [.showHideTransitionViews,.allowAnimatedContent], animations: {
            self.collectionView2.performBatchUpdates({
                self.collectionView2.reloadSections(IndexSet.init(integer: IndexSet.Element.init(0)))
            }, completion: nil)
        }, completion: nil)
        
        
    }
    
    @objc func rotate(){
        let cnt = self.commericals.count
        
        if(cnt > 1){
            if self.x1 != -1 {
                x1 = self.pageControl.currentPage + 1
            }else{
                self.x1 = 0
            }
        }
        else if (cnt == 1 || x1 > cnt - 1) {
            x1 = 0;
        }
        
        let u = x1 % cnt
        
        
        self.collectionView.setContentOffset(CGPoint(x: self.collectionView.frame.size.width * CGFloat(u), y: 0) , animated: true)
    }
    
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
    }
    
    
}


extension AdsListVC : UICollectionViewDelegate, UICollectionViewDataSource,UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if collectionView == self.collectionView{
            return commericals.count
        }else{
            return ads.count
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if collectionView == self.collectionView{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
            cell.imageName = self.commericals[indexPath.row]
            return cell
        }else{
            
            let identifier = "cell-\(self.viewsType)"
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: identifier, for: indexPath) as! AdCell
            
            cell.ad = self.ads[indexPath.row]
            return cell
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if collectionView == self.collectionView2{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
            vc.tamplateId = self.ads[indexPath.row].tamplate_id.intValue
            vc.ad = self.ads[indexPath.row]
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        if collectionView == self.collectionView{
            return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
        }else{
            if self.viewsType == 1{
                return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width * 2 / 3)
            }else if self.viewsType == 2{
                return CGSize.init(width: collectionView.frame.width / 2, height: UIScreen.main.bounds.width * 3 / 4)
            }else{
                return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width * 477 / 1128)
            }
        }
    }
    
    
    //Delegate With ScrollView (scroll item photos)
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView{
            if self.commericals.count > 0 {
                if self.collectionView.visibleCells.count > 0{
                    let pp = round(self.collectionView.contentOffset.x / self.collectionView.frame.size.width)
                    self.pageControl.set(progress: Int(pp), animated: true)
                    
                    self.timer.invalidate()
                    self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                }
            }
        }
    }
    
}
